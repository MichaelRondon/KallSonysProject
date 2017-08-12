(function() {
    'use strict';
    angular
        .module('omsApp')
        .factory('DatoContacto', DatoContacto);

    DatoContacto.$inject = ['$resource'];

    function DatoContacto ($resource) {
        var resourceUrl =  'api/dato-contactos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
