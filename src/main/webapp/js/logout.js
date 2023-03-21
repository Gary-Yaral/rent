let btn = document.querySelector("#btn-logout");
btn.onclick = on;

function on(e) {
	e.preventDefault();
	const formData = new FormData();
	formData.append("action", "logout");
	fetch("/rent/users/user", {
		method: "POST",
		body: formData
	}).then(res => res.json())
	.then(res => {
		if(res.logout) {window.location = "/rent/"}
	})
}