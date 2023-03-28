import { searchCanton, searchProvince } from "./provinces.js"
const cards = document.querySelector("#cards")
const house = document.getElementById('house')
const btnBusinnes = document.getElementById('bussines')
const body = document.querySelector('body')
const PATH = "./page";
const whatsApi = (phone, data) =>`https://api.whatsapp.com/send?phone=${phone}&text=${data}`;

btnBusinnes.onclick = () => window.location = PATH

body.onclick = (e) => {
	const isWhats =e.target.classList.contains("fa-whatsapp")
	if(isWhats) {
		redirectTo(e)
	}
}



const formData = new FormData()
formData.append("action", "get_house")
formData.append("house_id", house.value)
fetch(PATH, {
	method:"POST",
	body: formData
})
.then(res=> res.json())
.then(data=> {
	if(!data.house) {
		window.location = "./page"
		cards.innerHTML = templateEmpty
		
	} else {
		const {house, urls, user} = data
		cards.innerHTML = ""
		cards.innerHTML += template(house, urls, user)		
	}
})

const templateEmpty = `	<div class="empty">No hay datos para mostrar<div>`

const imgTemplate = (url)  => { return `<div class="carousel-item active">
	        <img src="./uploads/${url}" class="d-block w-100" alt="${url}">
	      </div>`}


function generateImgs(array) {
	let imgs = ""
	array.forEach(img => {
		imgs += imgTemplate(img)
	})
	
	return imgs
}

function redirectTo(e) {
	
	if(e.target.classList.contains("fa-whatsapp")) {
		const phone = e.target.getAttribute("phone")
		const message = e.target.getAttribute("message")
		window.location = whatsApi(phone, message)
		
	} else {
		let uri = `${PATH}?house=${house}`
		window.location = uri		
	}
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
	
	description = house.description
	return `
	<div class="card card-grid">
		<h3 class="header-page">${house.title}, ${province}, ${canton}<h3>
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
	    <h5 class="card-title">${house.title}</h5>
	    <p class="card-text price-text">$${house.price}</p>	    
	    <p class="card-text">${province}, ${canton}</p>
	    <p class="card-text"></p>
	    <p class="card-text"><strong>Dirección</strong></p>
	    <p class="card-text">- ${house.address}</p>
	    <p class="card-text"></p>
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
	    <p class="card-text">
	    	<h4><strong>Detalles</strong></h4>
	    	${description}
	    </p>
	    <i class="fa-brands fa-whatsapp" phone="${user.telephone}" message="${message}"></i>
	    <p class="card-text"></p>
	    <div class="card-user">Publicado por: ${user.name} ${user.lastName}</div>
	  </div>
	</div>`
}

function toCapitalize(str){
	let newStr = str.toLowerCase().split("")
	newStr[0] = newStr[0].toUpperCase()
	return newStr.join("")
}