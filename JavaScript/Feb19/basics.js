document.addEventListener("DOMContentLoaded", () => {

    const taskInput = document.getElementById("taskInput");
    const dueDateInput = document.getElementById("dueDateInput");
    const addBtn = document.getElementById("addBtn");
    const taskList = document.getElementById("taskList");

    let tasks = JSON.parse(localStorage.getItem("tasks")) || [];
    let filter = "all";

    function saveTasks() {
        localStorage.setItem("tasks", JSON.stringify(tasks));
    }

    function addTask() {
        const text = taskInput.value.trim();
        const dueDate = dueDateInput.value;

        if (text === "") return;

        tasks.push({
            id: Date.now(),
            text,
            dueDate,
            completed: false
        });

        taskInput.value = "";
        dueDateInput.value = "";
        saveTasks();
        renderTasks();
    }

    function deleteTask(id) {
        tasks = tasks.filter(task => task.id !== id);
        saveTasks();
        renderTasks();
    }

    function toggleTask(id) {
        const task = tasks.find(task => task.id === id);
        task.completed = !task.completed;
        saveTasks();
        renderTasks();
    }

    function editTask(id) {
        const task = tasks.find(task => task.id === id);
        const newText = prompt("Edit task:", task.text);
        if (!newText) return;

        task.text = newText.trim();
        saveTasks();
        renderTasks();
    }

    function renderTasks() {
        taskList.innerHTML = "";

        let filtered = tasks;

        if (filter === "completed") {
            filtered = tasks.filter(t => t.completed);
        } else if (filter === "pending") {
            filtered = tasks.filter(t => !t.completed);
        }

        filtered.forEach(task => {

            const li = document.createElement("li");
            li.draggable = true;
            li.dataset.id = task.id;

            if (task.completed) {
                li.classList.add("completed");
            }

            const row = document.createElement("div");
            row.classList.add("task-row");

            const span = document.createElement("span");
            span.textContent = task.text;

            const btnContainer = document.createElement("div");

            const completeBtn = document.createElement("button");
            completeBtn.textContent = task.completed ? "Undo" : "Complete";
            completeBtn.onclick = () => toggleTask(task.id);

            const editBtn = document.createElement("button");
            editBtn.textContent = "Edit";
            editBtn.onclick = () => editTask(task.id);

            const deleteBtn = document.createElement("button");
            deleteBtn.textContent = "Delete";
            deleteBtn.onclick = () => deleteTask(task.id);

            btnContainer.appendChild(completeBtn);
            btnContainer.appendChild(editBtn);
            btnContainer.appendChild(deleteBtn);

            row.appendChild(span);
            row.appendChild(btnContainer);

            li.appendChild(row);

            if (task.dueDate) {
                const small = document.createElement("small");
                small.textContent = "Due: " + task.dueDate;
                li.appendChild(small);
            }

            taskList.appendChild(li);
        });

        enableDragDrop();
    }

    function enableDragDrop() {

        const items = document.querySelectorAll("#taskList li");

        items.forEach(item => {

            item.addEventListener("dragstart", () => {
                item.classList.add("dragging");
            });

            item.addEventListener("dragend", () => {
                item.classList.remove("dragging");
                updateOrder();
            });
        });

        taskList.addEventListener("dragover", e => {
            e.preventDefault();
            const dragging = document.querySelector(".dragging");
            const afterElement = getDragAfterElement(taskList, e.clientY);

            if (afterElement == null) {
                taskList.appendChild(dragging);
            } else {
                taskList.insertBefore(dragging, afterElement);
            }
        });
    }

    function getDragAfterElement(container, y) {
        const elements = [...container.querySelectorAll("li:not(.dragging)")];

        return elements.reduce((closest, child) => {
            const box = child.getBoundingClientRect();
            const offset = y - box.top - box.height / 2;

            if (offset < 0 && offset > closest.offset) {
                return { offset: offset, element: child };
            } else {
                return closest;
            }
        }, { offset: Number.NEGATIVE_INFINITY }).element;
    }

    function updateOrder() {
        const ids = [...taskList.children].map(li => Number(li.dataset.id));
        tasks.sort((a, b) => ids.indexOf(a.id) - ids.indexOf(b.id));
        saveTasks();
    }

    window.setFilter = function(type) {
        filter = type;
        renderTasks();
    };

    addBtn.addEventListener("click", addTask);

    taskInput.addEventListener("keypress", e => {
        if (e.key === "Enter") addTask();
    });

    renderTasks();
});