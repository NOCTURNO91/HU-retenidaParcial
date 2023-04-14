moduloCommon.factory( "ClientesApi", function($resource) {
    
    var resource = $resource( "rest/clientes/:action",
	{
            action: "@action"
        },
        {
            consultar: { method: "POST", isArray: false },
            listar: { method: "GET", isArray: false },
            obtener: { method: "POST", isArray: false }
        }
    );
	
    return resource;
        
});