export function validateAdd(form) {
	const {image, description, price, address} = form
	const fields = [image, description, price, address]
	let error = "<ul>"
	let counter = fields.length;

	if(image.files.length == 0) {
		error += "<li>Debes seleccionar una imagen</li>"
		counter--
	}
	
	if(address.value === "") {
		error += "<li>Debes ingresar la dirección</li>"
		counter--
	}
	
	if(price.value === ""){
		error += "<li>Debes ingresar el precio</li>"
		counter--
	}
	
	if(description.value === "") {
		error += "<li>Debes ingresar la descripción</li>"
		counter--
	}
	
	error += "</ul>"
	return {
		isValid: fields.length === counter,
		error
	}
}

export function validateEdit(form) {
	const {description, price, address} = form
	const fields = [description, price, address]
	let error = "<ul>"
	let counter = fields.length;
	
	if(address.value === "") {
		error += "<li>Debes ingresar la dirección</li>"
		counter--
	}
	
	if(price.value === ""){
		error += "<li>Debes ingresar el precio</li>"
		counter--
	}
	
	if(description.value === "") {
		error += "<li>Debes ingresar la descripción</li>"
		counter--
	}
	
	error += "</ul>"
		
	return {
		isValid: fields.length === counter,
		error
	}
}

export function deleteHouse(id, path, loadTable) {
	const formData = new FormData();
	formData.append("action", "delete-house");
	formData.append("house_id", id)
	fetch(path, {
		method: "POST",
		body: formData
	})
	.then(res => res.json())
	.then(json => {
		if(json.delete) {
			Swal.fire({
			    icon: 'success',
			    title: 'OK',
			    text: 'Casa ha sido eliminada correctamente',
			})
			loadTable()
		} else {
			Swal.fire({
			    icon: 'error',
			    title: 'Oops!',
			    text: '!Ha ocurrido un error!',
			})
		}
	})
}