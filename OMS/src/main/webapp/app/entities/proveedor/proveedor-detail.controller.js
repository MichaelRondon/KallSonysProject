(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('ProveedorDetailController', ProveedorDetailController);

    ProveedorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Proveedor', 'DatoContacto', 'ProveedorProducto'];

    function ProveedorDetailController($scope, $rootScope, $stateParams, previousState, entity, Proveedor, DatoContacto, ProveedorProducto) {
        var vm = this;

        vm.proveedor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omsApp:proveedorUpdate', function(event, result) {
            vm.proveedor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
