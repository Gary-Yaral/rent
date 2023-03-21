export function loadRentalTable(form, PATH_DASHBOARD) {
	const houseTable = document.getElementById("rental");
	// Si la tabla no está visible detiene la ejecución
	if(!houseTable) return
	
	// Si la tabla existe entonces se crea todo
	let id = form.user_id.value
	var formData = new FormData()
	formData.append('action','load-rental');
	formData.append('user_id',id);
	fetch(PATH_DASHBOARD, {
		method: "POST",
		body: formData
	})
	.then(res => res.json())
	.then(json => {		
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
				];
			});	
			opt.data = dataTable	
		} 
			
		$('#rental').DataTable(opt);
	})
	
}