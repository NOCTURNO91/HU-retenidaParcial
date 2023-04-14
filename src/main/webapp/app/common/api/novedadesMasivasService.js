moduloCommon.factory( "NovedadesMasivasApi", function($resource) {
    
    var resource = $resource( "rest/novedades/:action/:remeIdInt/:ipidIdInt",
	{
            action: "@action",
            remeIdInt: "@remeIdInt",
            ipidIdInt: "@ipidIdInt"
        },
        {
            consultar: { method: "POST", isArray: false },
            listar: { method: "GET", isArray: false },
            obtener: { method: "POST", isArray: false } 
        }
    );
	
    return resource;
        
} );