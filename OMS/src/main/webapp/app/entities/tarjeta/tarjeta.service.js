(function() {
    'use strict';
    angular
        .module('omsApp')
        .factory('Tarjeta', Tarjeta);

    Tarjeta.$inject = ['$resource'];

    function Tarjeta ($resource) {
        var resourceUrl =  'api/tarjetas/:id';

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
