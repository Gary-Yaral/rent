import { searchCanton, searchProvince } from "./provinces.js"
const cards = document.querySelector("#cards")
const house = document.getElementById('house')
const btnBusinnes = document.getElementById('bussines')
const PATH = "./page";

btnBusinnes.onclick = () => window.location = PATH


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
const template = (house, urls, user) => {
	let province = searchProvince(house.province)
	province = toCapitalize(province)	
	let canton = searchCanton(house.province, house.canton)
	canton = toCapitalize(canton)	
	let description = ""
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
	    <i class="fa-brands fa-whatsapp"></i>
	    <div class="card-user">Publicado por: ${user.name} ${user.lastName}</div>
	  </div>
	</div>`
}

function toCapitalize(str){
	let newStr = str.toLowerCase().split("")
	newStr[0] = newStr[0].toUpperCase()
	return newStr.join("")
}