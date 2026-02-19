const taskInput = document.getElementById("taskInput");
const addBtn = document.getElementById("addBtn");
const taskList = document.getElementById("taskList");

let tasks = JSON.parse(localStorage.getItem("tasks")) || [];

function saveTasks() {
    localStorage.setItem("tasks", JSON.stringify(tasks));
}

function renderTasks() {
    taskList.innerHTML = "";
 
    tasks.forEach((task, index) => {
 
        const li = document.createElement("li");
        li.textContent = task.text;
 
        if (task.completed) {
            li.classList.add("completed");
        }
 
        // Toggle complete
        li.addEventListener("click", () => {
            tasks[index].completed = !tasks[index].completed;
            saveTasks();
            renderTasks();
        });

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "Delete";
 
        deleteBtn.addEventListener("click", (e) => {
            e.stopPropagation();
            tasks.splice(index, 1);
            saveTasks();
            renderTasks();
        });
 
        li.appendChild(deleteBtn);
        taskList.appendChild(li);
    });
}
renderTasks();
addBtn.addEventListener("click", () => {
 
    const text = taskInput.value.trim();
 
    if (text === "") {
        alert("Task cannot be empty");
        return;
    }
 
    tasks.push({
        text: text,
        completed: false
    });
 
    taskInput.value = "";
    saveTasks();
    renderTasks();
});
 