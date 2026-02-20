const API = "http://localhost:3000/books";

const list = document.getElementById("bookList");
const search = document.getElementById("search");
const authorFilter = document.getElementById("authorFilter");
const categoryFilter = document.getElementById("categoryFilter");
const addBtn = document.getElementById("addBtn");

async function loadBooks() {
  const res = await fetch(API);
  const books = await res.json();
  render(books);
  loadCategories(books);
}

function render(books) {
  list.innerHTML = "";

  books.forEach(book => {
    const li = document.createElement("li");
    li.className = book.available ? "available" : "issued";

    li.innerHTML = `
      <strong>${book.title}</strong> 
      - ${book.author} 
      [${book.category}]
    `;

    const issueBtn = document.createElement("button");
    issueBtn.textContent = book.available ? "Issue" : "Return";
    issueBtn.onclick = () => toggleBook(book);

    const editBtn = document.createElement("button");
    editBtn.textContent = "Edit";
    editBtn.onclick = () => editBook(book);

    const delBtn = document.createElement("button");
    delBtn.textContent = "Delete";
    delBtn.onclick = () => deleteBook(book.id);

    li.append(issueBtn, editBtn, delBtn);
    list.appendChild(li);
  });
}

async function addBook() {
  const title = document.getElementById("title").value;
  const author = document.getElementById("author").value;
  const category = document.getElementById("category").value;

  if (!title || !author || !category) return;

  await fetch(API, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      title,
      author,
      category,
      available: true
    })
  });

  loadBooks();
}

addBtn.onclick = addBook;

async function toggleBook(book) {
  await fetch(`${API}/${book.id}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ available: !book.available })
  });
  loadBooks();
}

async function deleteBook(id) {
  await fetch(`${API}/${id}`, {
    method: "DELETE"
  });
  loadBooks();
}

async function editBook(book) {
  const newTitle = prompt("Edit Title", book.title);
  const newAuthor = prompt("Edit Author", book.author);
  const newCategory = prompt("Edit Category", book.category);

  if (!newTitle || !newAuthor || !newCategory) return;

  await fetch(`${API}/${book.id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      ...book,
      title: newTitle,
      author: newAuthor,
      category: newCategory
    })
  });

  loadBooks();
}

search.oninput = async () => {
  const res = await fetch(API);
  const books = await res.json();
  const term = search.value.toLowerCase();

  render(
    books.filter(b =>
      b.title.toLowerCase().includes(term)
    )
  );
};

authorFilter.oninput = async () => {
  const res = await fetch(API);
  const books = await res.json();
  const term = authorFilter.value.toLowerCase();

  render(
    books.filter(b =>
      b.author.toLowerCase().includes(term)
    )
  );
};

function loadCategories(books) {
  const categories = [...new Set(books.map(b => b.category))];

  categoryFilter.innerHTML =
    `<option value="">All Categories</option>`;

  categories.forEach(cat => {
    const option = document.createElement("option");
    option.value = cat;
    option.textContent = cat;
    categoryFilter.appendChild(option);
  });
}

categoryFilter.onchange = async () => {
  const res = await fetch(API);
  const books = await res.json();

  if (!categoryFilter.value) return render(books);

  render(
    books.filter(b =>
      b.category === categoryFilter.value
    )
  );
};

loadBooks();