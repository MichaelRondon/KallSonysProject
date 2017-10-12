(function () {
    'use strict';
    angular
            .module('omsApp')
            .service('ImageProduct', ImageProduct);

    ImageProduct.$inject = ['$resource', 'DateUtils'];

    function ImageProduct($resource, DateUtils) {
//        var resourceUrl =  'api/productos/:id';
        var tamanio = '';

        return {
            getTamanio: function () {
                return tamanio;
            },
            setTamanio: function(value) {
                tamanio = value;
            }
        };
    }
})();
