//import { loadRentalTable } from "./rental.js"
import { searchCanton, searchProvince } from "./provinces.js"

const hamburgerBtn = document.querySelector('.hamburger');
const sidebar = document.querySelector('.options');
const body = document.querySelector("body")
const user = document.querySelector("#user")
const PATH_DASHBOARD = '/rent/dashboard/'
let btnOut = document.querySelector("#btn-logout");
let home = document.getElementById("home")
const modal = document.getElementById('all-images')

if(home) {
	getDataHome ()
}

btnOut.onclick = logout;

function logout(e) {
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

hamburgerBtn.addEventListener('click', () => {
  sidebar.classList.toggle('open');
})

$(document).ready(function () {
	loadHousesTable()
});

body.onclick = function(e) {
	
	const element = e.target
	const {classList} = element
	const isOptPane = classList.contains("goTo")
	const isBtnDelete = classList.contains("btn-delete")
	const isOut = e.target.id === "btn-logout"
	const isBtnShow = classList.contains("show-images")
	
	if(isOptPane) {
		window.location = PATH_DASHBOARD + e.target.id.split("/")[1]
	}
	
	if(isOut) {
		logout(e)
	}
	
	if(isBtnShow) {
		showModal(e)
	}
	
	if(isBtnDelete) {
		deleteHouse(e)
	}
}

function loadHousesTable() {
	const houseTable = document.getElementById("houses");
	// Si la tabla no está visible detiene la ejecución
	if(!houseTable) return
	
	// Si la tabla existe entonces se crea todo
	var formData = new FormData()
	formData.append('action','load-houses');
	formData.append('user_id',user.value);
	fetch(PATH_DASHBOARD, {
		method: "POST",
		body: formData
	})
	.then(res => res.json())
	.then(json => {	
		var btns = (id) => {return `
			<div>
				<a href="./edit-house?house=${id}" class="btn btn-primary btn-edit" house="${id}">Editar</a>	
				<button class="btn btn-danger btn-delete" house="${id}">Eliminar</button>	
			</div>
		`}
		
		const img = (id)=>`<button class="btn btn-secondary show-images" house="${id}">Ver</button>`
		const opt = {
				responsive:true,
				bDestroy: true
		}
		
		if(json.length > 0) {
			var dataTable = json.map((house, index) => {
		    	return [
					index + 1, 
					img(house.id),
					searchProvince(house.province), 
					searchCanton(house.province, house.canton), 
					house.address, 
					house.price, 
					house.bathrooms, 
					house.rooms, 
					house.area, 
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

function deleteHouse(e) {
	const house_id = e.target.getAttribute("house")

	var formData = new FormData()
	formData.append('action','delete-house');
	formData.append('house_id',house_id);
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

function getDataHome () {
	var formData = new FormData()
	formData.append('action','get-home-data');
	formData.append('user_id',user.value);
	fetch(PATH_DASHBOARD, {
		method: 'POST',
		body: formData
	})
	.then(res=>res.json())
	.then(res=>{
		document.getElementById("houses-total").innerHTML = res.houses
		document.getElementById("availables").innerHTML = res.availables
	})
}


function showModal(e) {
	let houseId = e.target.getAttribute("house")
	console.log(houseId)
}


