import { validateAdd, validateEdit, deleteHouse } from "./houseActions.js"
import { loadInput } from "./inputLoader.js"
import { loadRentalTable } from "./rental.js"

const hamburgerBtn = document.querySelector('.hamburger');
const sidebar = document.querySelector('.options');
const formModal = document.querySelector('#form-modal')
const body = document.querySelector("body")
const img = document.getElementById("preview")
const select = document.querySelector("#select-status")
const formTitle =  document.querySelector("#form-house-title")
const PATH_DASHBOARD = '/rent/dashboard/'
const form = formModal.querySelector("form")

loadInput(img) // Esto controla lo que se carga o no en el input file de agregar casa
loadRentalTable(form, PATH_DASHBOARD) 
hamburgerBtn.addEventListener('click', () => {
  sidebar.classList.toggle('open');
})
$(document).ready(function () {
	loadHousesTable()
});

body.onclick = function(e) {
	
	const element = e.target
	const {classList} = element
	const isBtnClose = classList.contains("btn-close")
	const isModal = classList.contains("form-modal")
	const isAddHouse = classList.contains("add-house")
	const isNewHouse = classList.contains("new-house")
	const isOptPane = classList.contains("goTo")
	const isBtnEdit = classList.contains("btn-edit")
	const isBtnDelete = classList.contains("btn-delete")
	
	if(isNewHouse) {
		form.classList.remove("form-edit")
		form.classList.add("form-save")
		formTitle.innerHTML = "Agregar"
		createOption(["Disponible"], select)
		formModal.classList.remove("modal-hidden")
	}
	
	if(isBtnClose || isModal) {
		resetForm()
	}
	
	if(isAddHouse) {
		if(form.classList.contains("form-edit")) {
			editHouse(e)			
		}
		
		if(form.classList.contains("form-save")){			
			saveHouse(e)
		}
	}
	
	if(isOptPane) {
		window.location = PATH_DASHBOARD + e.target.id.split("/")[1]
	}
	
	if(isBtnDelete) {
		let id = e.target.getAttribute("house")
		deleteHouse(id, PATH_DASHBOARD, loadHousesTable)
	}
	
	if(isBtnEdit) {
		let id = e.target.getAttribute("house") 
		createOption(["Disponible", "Alquilado"], select)
		loadHouse(form, id)
		formTitle.innerHTML = "Editar"
		form.classList.add("form-edit")
		form.classList.remove("form-save")
		form.house_id.value = id
		formModal.classList.remove("modal-hidden")
	}
}

function loadHousesTable() {
	const houseTable = document.getElementById("houses");
	// Si la tabla no está visible detiene la ejecución
	if(!houseTable) return
	
	// Si la tabla existe entonces se crea todo
	let id = form.user_id.value
	var formData = new FormData()
	formData.append('action','load-houses');
	formData.append('user_id',id);
	fetch(PATH_DASHBOARD, {
		method: "POST",
		body: formData
	})
	.then(res => res.json())
	.then(json => {	
		var btns = (id) => {return `
			<div>
				<button class="btn btn-primary btn-edit" house="${id}">Editar</button>	
				<button class="btn btn-danger btn-delete" house="${id}">Eliminar</button>	
			</div>
		`}
		
		var img = (path) => {return `
			<img src="../uploads/${path}" class="img-house" />
		 `}
		const opt = {
				responsive:true,
				bDestroy: true
		}
		
		if(json.length > 0) {
			var dataTable = json.map((house, index) => {
		    	return [
					index + 1, 
					img(house.photo),
					house.address, 
					house.price, 
					house.description, 
					house.status,
					btns(house.id)
				];
			});	
			opt.data = dataTable	
		} 
			
		$('#houses').DataTable(opt);
	})
	
}

function saveHouse(e) {
	e.preventDefault()
	const validator = validateAdd(form)
	if(!validator.isValid) {
		Swal.fire({
		    icon: 'warning',
		    title: 'Atención',
		    html: validator.error,
		})
		return
	}
	var formData = new FormData(form)
	formData.append('action','save-house');
	fetch(PATH_DASHBOARD, {
		method: 'POST',
		body: formData
	})
	.then(res=>res.json())
	.then(json=>{
		if(json.result) {
			Swal.fire({
			    icon: "success",
			    title: 'OK',
			    text: json.message,
			})
			resetForm()
			loadHousesTable()		
		} else {
			Swal.fire({
			    icon: "error",
			    title: 'OK',
			    text: json.message,
			})
		}
	})
}

function editHouse(e) {
	e.preventDefault()
	const validator = validateEdit(form)
	if(!validator.isValid) {
		Swal.fire({
		    icon: 'warning',
		    title: 'Atención',
		    html: validator.error,
		})
		return
	}

	var formData = new FormData(form)
	formData.append('action','edit-house');
	formData.append('house_id',form.house_id.value);
	fetch(PATH_DASHBOARD, {
		method: 'POST',
		body: formData
	})
	.then(res=> res.json())
	.then(json=> {
		if(json.result) {
			Swal.fire({
		    icon: "success",
		    title: "OK",
		    text: json.message,
			})
			.then((result) => {
			  if (result.isConfirmed || result.isDismissed ) {
				window.location = ""		  
			  }
			})
		} else {
			Swal.fire({
			    icon: "error",
			    title: 'Oops!',
			    text: json.message,
			})		
		} 
	})
}


function createOption(data, parent) {
	parent.innerHTML = ""
	data.forEach(value => {
		const option = document.createElement("option")		
		option.value = value
		option.innerHTML = value
		parent.appendChild(option)
	})
}

function loadHouse(form, id) {
	let formData = new FormData()
	formData.append("action","get-house");
	formData.append("house_id", id );
	fetch(PATH_DASHBOARD, {
		method: "POST",
		body: formData
	})
	.then(res=> res.json())
	.then(json=> {
		if(json.error) {
			alert(json.error)
			return
		}
		
		const img = form.querySelector("img")
		img.src = "../uploads/" + json.photo 
		form.price.value = json.price
		form.address.value = json.address
		form.description.value = json.description
		form.status.value = json.status		
	})
}

function resetForm(){
	formModal.classList.add("modal-hidden")
	form.reset()
	img.src=""
}


