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
        vm.producImageSmallBaseUrl = 'http://laptop-diego:9091/api/ImageSmall/';
        vm.producImageMediumBaseUrl = 'http://laptop-diego:9091/api/ImageMedium/';
        vm.producImageLargeBaseUrl = 'http://laptop-diego:9091/api/ImageLarge/';

        var unsubscribe = $rootScope.$on('omsApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
