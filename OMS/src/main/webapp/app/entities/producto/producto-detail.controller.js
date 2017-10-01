(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('ProductoDetailController', ProductoDetailController);

    ProductoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Producto', 'ProveedorProducto', 'Categoria'];

    function ProductoDetailController($scope, $rootScope, $stateParams, previousState, entity, Producto, ProveedorProducto, Categoria) {
        var vm = this;

        vm.producto = entity;
        vm.previousState = previousState.name;
        vm.producImageSmallBaseUrl = 'http://laptop-diego:9091/api/imageSmall/';

        var unsubscribe = $rootScope.$on('omsApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
