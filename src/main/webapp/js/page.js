import { searchCanton, searchProvince } from "./provinces.js"
const modal = document.querySelector(".modal-customer")
const cards = document.querySelector("#cards")
const body = document.querySelector('body')
const btnBusinnes = document.getElementById('bussines')
const PATH = "./page";
const whatsApi = (phone, data) =>`https://api.whatsapp.com/send?phone=${phone}&text=${data}`;
 

loadHouses()
btnBusinnes.onclick = () => window.location = PATH
body.onclick = (e) => {
	const isCard = e.target.classList.contains("card-grid")
	if(isCard) {
		let id = e.target.getAttribute("house")
		let uri = `${PATH}?house=${id}` 
		window.location = uri
	}
}

function redirectTo(house, e) {
	
	if(e.target.classList.contains("fa-whatsapp")) {
		const phone = e.target.getAttribute("phone")
		const message = e.target.getAttribute("message")
		window.location = whatsApi(phone, message)
		
	} else {
		let uri = `${PATH}?house=${house}`
		window.location = uri		
	}
}

function loadHouses() {
	const formData = new FormData()
	formData.append("action", "get_all_houses")
	fetch(PATH, {
		method:"POST",
		body: formData
	})
	.then(res=> res.json())
	.then(json=> {
		cards.innerHTML = ""
		if(json.length === 0) {
			cards.innerHTML = templateEmpty
		} else {
			json.forEach(({house, urls, user}) => {	
				cards.innerHTML += template(house, urls, user)
			})		
			const cardsBlock = document.querySelectorAll('.card-body')
			cardsBlock.forEach(card => {
				let house = card.querySelector(".hidden").value
				card.onclick = (e) => redirectTo(house, e)
			})
		}
	})
}

const imgTemplate = (url)  => { return `<div class="carousel-item active">
	        <img src="./uploads/${url}" class="d-block w-100" alt="${url}">
	      </div>`}

const templateEmpty = `<div class="empty-house">No hay casa registradas aún</div>`

function generateImgs(array) {
	let imgs = ""
	array.forEach(img => {
		imgs += imgTemplate(img)
	})
	
	return imgs
}
const template = (house, urls, user) => {
	let province = searchProvince(house.province)
	province = toCapitalize(province)	
	let canton = searchCanton(house.province, house.canton)
	canton = toCapitalize(canton)	
	let description = ""
	let message = "%0A*Asunto*%0A"
	message += "%0A*Deseo más información sobre su post*%0A"
	message += house.title 
	
	if(house.description.length > 100) {
		description = house.description.slice(0, 97) + "..."
	} else {
		description = house.description
	}
	return `

	<div class="card card-grid">
	  <div id="house-${house.id}" class="carousel slide" data-bs-ride="carousel">
	    <div class="carousel-inner">${generateImgs(urls)}</div>
	    <button class="carousel-control-prev" type="button" data-bs-target="#house-${house.id}" data-bs-slide="prev">
	      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	      <span class="visually-hidden">Anterior</span>
	    </button>
	    <button class="carousel-control-next" type="button" data-bs-target="#house-${house.id}" data-bs-slide="next">
	      <span class="carousel-control-next-icon" aria-hidden="true"></span>
	      <span class="visually-hidden">Siguiente</span>
	    </button>
	  </div>
	  <div class="card-body">
	  	<input type="hidden" class="hidden" value="${house.id}">
	    <h5 class="card-title">${house.title}</h5>
	    <p class="card-text price-text">$${house.price}</p>
	    <p class="card-text">${province}, ${canton}</p>
	    <div class="card-items">
	    	<div>
	    		<i class="fa-solid fa-bed"></i>
	    		<span>${house.rooms} habitaciones </span>
	    	</div>
	    	<div>
		    	<i class="fa-solid fa-toilet"></i>
		    	<span> ${house.bathrooms} baños</span>
	    	</div>
	    	<div>
		    	<i class="fa-solid fa-maximize"></i>
		    	<span> ${house.area} Km<sup>2<sup></span>
	    	</div>
	    </div>
	    <p class="card-text">${description}</p>
	    <i class="fa-brands fa-whatsapp" phone="${user.telephone}" message="${message}"}></i>
	    <div class="card-user">Publicado por: ${user.name} ${user.lastName}</div>
	  </div>
	</div>`
}

function toCapitalize(str){
	let newStr = str.toLowerCase().split("")
	newStr[0] = newStr[0].toUpperCase()
	return newStr.join("")
}




