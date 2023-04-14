moduloCommon.factory( "UsuarioApi", function($resource) {
    
    var resource = $resource( "rest/api/usuario/:action",
	{
            action: "@action"
        },
        {
            consultar: { method: "POST", isArray: false },
            listar: { method: "GET", isArray: false },
            roles: { method : "GET", isArray : true }
            /*
            eliminar: {method:'GET', isArray:false, headers:{'Content-Type':'application/json;charset=UTF-8'}},
            eliminarRango: {method:'GET', isArray:false, headers:{'Content-Type':'application/json;charset=UTF-8'}}
            */
        }
    );
	
    return resource;
        
});