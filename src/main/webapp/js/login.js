let form = document.querySelector("form")
		
form.onsubmit = (e) => {
	e.preventDefault()
	const formData = new FormData(form)
	formData.append("action", "login")
	
	fetch("/rent/users/user", {
		method: "POST",
		body: formData
	})
	.then(res => res.json())
	.then(json => {
		if(json.access) {
			window.location = ""				
		} else {
			Swal.fire({
				    icon: "error",
				    title: "Oops!",
				    text: json.message,
					})
		}		
	})

}