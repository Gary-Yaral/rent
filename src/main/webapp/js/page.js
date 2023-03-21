const cards = document.querySelector("#cards")
const PATH = "./page";

const formData = new FormData()
formData.append("action", "get_all_houses")
fetch(PATH, {
	method:"POST",
	body: formData
})
.then(res=> res.json())
.then(json=> {
	console.log(json)
	cards.innerHTML = ""
	json.forEach(house => {
		cards.innerHTML += template(house)
	})
})

const template = (house) => {return `
	<div class="col-lg-4 col-md-6 col-sm-12 mb-4">
		<div class="card h-100">
			<img src="./uploads/${house.photo}" class="card-img-top"
				alt="Product Image">
			<div class="card-body">
				<h5 class="card-title text-primary">Detalle</h5>
				<p class="card-text">${house.description}</p>
				<p class="card-text"><strong class="text-primary">Ubicada en: </strong> ${house.address}</p>
				<p class="card-text"><strong>Alquiler:</strong> $${house.price}</p>
				<button class="btn btn-primary">Quiero alquilar <i class="fa-solid fa-heart"></i></button>
			</div>
		</div>
	</div>`
}