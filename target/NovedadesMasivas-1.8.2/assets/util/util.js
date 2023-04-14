var Util =
{
    mostrarCargando: function()
    {
        $.loader( {
            className: "blue-with-image-2",
            content: ""
        } );
    },
    
    ocultarCargando: function()
    {
        $.loader( "close" );
    },
    
    getKeyCode: function( event )
    {
        return event.keyCode || event.charCode;
    },
    
    isObjectEmpty: function( obj )
    {
        for ( var key in obj )
        {
            if ( obj.hasOwnProperty( key ) ) {
                return false;
            }
        }
        
        return true;
    },
    
    getObjectSize: function( obj )
    {
        var size = 0, key;
        
        for ( key in obj )
        {
            if ( obj.hasOwnProperty( key ) ) {
                size++;
            }
        }
        
        return size;
    },
    
    getUrlParams: function( paramsString )
    {
        var params = {};

        if ( paramsString !== null && paramsString !== "" )
        {
            var paramsArr = paramsString.split( "&" );
            var nameValue = [];

            for ( var i = 0; i < paramsArr.length; i++ )
            {
                nameValue = paramsArr[i].split( "=" );
                params[nameValue[0]] = nameValue[1];
            }
        }

        return params;
    }
};