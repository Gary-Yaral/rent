import { provincesData, searchCantones } from "./provinces.js"
const imgs = document.querySelector("#images")
const imgErrors = document.querySelector("#errors-imgs")
const preview = document.querySelector("#preview")
const selectProvince = document.querySelector("#province")
const selectCanton = document.querySelector("#canton")
const price = document.querySelector("#price")
const area = document.querySelector("#area")
const bathrooms = document.querySelector("#bathrooms")
const form =  document.querySelector('form')
const MAX_LIMIT = 20
const PATH_DASHBOARD = '/rent/dashboard/'

loadBathsAndRooms()
loadProvinces()

price.oninput = controlValue
area.oninput = controlValue
form.onsubmit = sendData

selectProvince.onchange = (e)=>{loadCanton(e)}

var allowedTypes = ['image/jpeg', 'image/png', 'image/jpg'];

imgs.addEventListener('change', function(e) {
  var invalidFiles = [];
  
  for (var i = 0; i < e.target.files.length; i++) {
    let file = imgs.files[i];
    if (allowedTypes.indexOf(file.type) === -1) {
      invalidFiles.push(file.name);
    }
  }
  
  if (invalidFiles.length > 0) {
	  const div = document.createElement('div')
	  div.innerHTML = "Archivos con formato no permitido"
	  div.classList.add("error-title")
	  imgErrors.appendChild(div)
	  invalidFiles.forEach(filename => {
		  const div = document.createElement('div')
		  div.innerHTML = `- ${filename}`
		  imgErrors.appendChild(div)
	  })
    imgs.value = '';
    preview.innerHTML = ""
  } else {
	 imgErrors.innerHTML = ""
	 loadInput(e, preview)
  }
});

function loadInput(e, container) {
	container.innerHTML = ""
	for (var i = 0; i < e.target.files.length; i++) {
		const img = document.createElement('img')
		const reader = new FileReader();
			reader.readAsDataURL(e.target.files[i])
			reader.onload = function(e) {
				img.src = e.target.result
			}
		
		container.appendChild(img)
		
	}	
}


function loadProvinces() {
	resetCanton()
	selectProvince.innerHTML = ""
	const emptyOption = document.createElement('option')
	emptyOption.value = ""
	emptyOption.innerHTML = "Selecciona la provincia"
	selectProvince.appendChild(emptyOption) 
	
	provincesData.forEach(data => {
		const opt = document.createElement('option')
		opt.value = data.code
		opt.innerHTML = data.province 
		selectProvince.appendChild(opt)
	})
}

function loadCanton({target:{value}}) {	
	resetCanton()
	searchCantones(value).forEach(data => {
		const opt = document.createElement('option')
		opt.value = data.code
		opt.innerHTML = data.canton 
		selectCanton.appendChild(opt)
	})
}

function resetCanton(){
	selectCanton.innerHTML = ""
	const emptyOption = document.createElement('option')
	emptyOption.value = ""
	emptyOption.innerHTML = "Selecciona cantón"
	selectCanton.appendChild(emptyOption) 
}

function loadBathsAndRooms() {
	
	bathrooms.appendChild(getEmpty()) 
	rooms.appendChild(getEmpty()) 
	
	for(let i = 1; i <= MAX_LIMIT; i++) {
		bathrooms.appendChild(getOption(i))
		rooms.appendChild(getOption(i))
	}
}

function getEmpty() {
	const emptyOption = document.createElement('option')
	emptyOption.value = "0"
	emptyOption.innerHTML = "NINGUNO"
	
	return emptyOption
}

function getOption(i) {
	const opt = document.createElement('option')
	opt.value = i
	opt.innerHTML = i
	return opt
}

function controlValue(e) {
	if(e.target.value < 0) {
		e.target.value = 0
	}
}

function sendData(e) {
	e.preventDefault()
	const formData = new FormData(form)
	formData.append("action", "save-house")
	fetch(PATH_DASHBOARD, {
		method: "POST",
		body: formData
	})
	.then(res => res.json())
	.then(res => {
		if(res.result) {
			alert("Datos añadidos correctamente")
			form.reset()
			preview.innerHTML = ""
		}
	})
}



