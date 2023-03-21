export function loadInput (img){
	const input = document.getElementById("img-loaded")
	
	let file = undefined
	input.onchange = function(e) {
	  saveLastData(e)
	  if(e.target.value !== "") {	  
		  const reader = new FileReader();
		  reader.readAsDataURL(e.target.files[0])
		  reader.onload = function(e) {
		    img.src = e.target.result
		  }
	  }
	  
	}
	
	function saveLastData(e) {
		const validExt = ["jpg", "jpeg", "png"]
	  if(e.target.files.length > 0) {
		let array = e.target.files[0].name.split(".")
		let ext = array[array.length - 1];
		if(validExt.includes(ext)) {
		    file = e.target.files
		    return 		
		} 
		alert("Formato no valido de imagen")
		if(!file){
			e.target.files = new DataTransfer().files;
		} else {
			e.target.files = file;
		}
	  } 
	
	  if(e.target.files.length === 0 && file) {
	    e.target.files = file
	  } 
	}
}