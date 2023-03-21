function validator(event, regExp, data, error) {
  // data = {user: "", password: ""}
  // error = {element: "", message: "Aqui va el error" }


  // Seleccionando el input que genera el evento
  const input = event.target
  // Extraigo las propiedades
  const { name, value } = input
  
  // Validando con la expresion regular el texto que escribimos
  const isValid = regExp.test(value)

  // Si no es valido el texto
  if(!isValid) {
    // Limpiamos la propiedad que tiene ese nombre
    data[name] = ""
    // Mostrar el mensaje de error de ese campo
    error.element.innerHTML = error.message
    return
  }
  // Guardamos el texto en su respectiva propiedad
  data[name] = value 
  // Limpiar la etiqueta del error
  error.element.innerHTML = ""

}

function isValidData(data) {
  // Extraigo las propiedades que tiene la data
  const props = Object.keys(data) // Array
  var counter = 0;
  // Recorriendo las propiedades de la data
  props.forEach((prop) => {
    // Verifico si la propiedad seleccionada es diferente de vacio o está llena
    if(data[prop] !== "") counter++
  })

  // comparamos si el numero de propiedades llenas coincide con el numero de propiedades total 
  return counter === props.length

}

export { isValidData, validator }