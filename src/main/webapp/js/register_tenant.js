const form = document.querySelector("form")
const PATH = "/rent/register"
form.onsubmit = (e) => {
	e.preventDefault()
	const formData = new FormData(form)
	formData.append("action", "register_tenant")
	fetch(PATH, {
		method: "POST",
		body: formData
	})
	.then(res=> res.json())
	.then(json=>{
		if(json.result) {
			Swal.fire({
			    icon: "success",
			    title: "OK",
			    text: json.message,
				})
				.then((result) => {
				  if (result.isConfirmed || result.isDismissed ) {
					form.reset()		  
				  }
				})
		} else {
			Swal.fire({
			    icon: "error",
			    title: "Oops!",
			    text: json.message,
				})
		}
	})
}