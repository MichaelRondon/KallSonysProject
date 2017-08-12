(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('ProveedorProductoDetailController', ProveedorProductoDetailController);

    ProveedorProductoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProveedorProducto', 'Proveedor', 'Producto'];

    function ProveedorProductoDetailController($scope, $rootScope, $stateParams, previousState, entity, ProveedorProducto, Proveedor, Producto) {
        var vm = this;

        vm.proveedorProducto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omsApp:proveedorProductoUpdate', function(event, result) {
            vm.proveedorProducto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
