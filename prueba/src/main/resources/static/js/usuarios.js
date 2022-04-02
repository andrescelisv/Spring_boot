// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuarios').DataTable();
  actualizaremaildelusuario();
});

function actualizaremaildelusuario(){
document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}
async function cargarUsuarios(){

  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders()

  });
  const usuarios = await request.json();


  let listadoHtml = '';

    for (let usuario of usuarios){

    let botoneliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    let telefonoTexto = usuario.telefono== null ? '-': usuario.telefono;
    let usuarioHTML = '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+' ' + usuario.apellido+'</td><td>'+telefonoTexto+'</td><td>'+usuario.email+'</td><td>'+botoneliminar+'</td></tr>';


   listadoHtml += usuarioHTML;


}

document.querySelector('#usuarios tbody').outerHTML=listadoHtml;
}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}

async function eliminarUsuario(id){
if(!confirm("¿Esta seguro que desea eliminar el usuario?")){
    return;
}
    const request = await fetch('api/usuarios/'+id, {
        method: 'DELETE',
        headers: getHeaders()

      });

      document.location.reload();



}



