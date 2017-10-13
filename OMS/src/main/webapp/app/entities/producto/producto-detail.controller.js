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
        vm.getKeyWordText = getKeyWordText;

        var unsubscribe = $rootScope.$on('omsApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function getKeyWordText(items) {
            var text = '';
            if (items) {
                var i;
                for (i = 0; i < items.length; i++) {
                    if(i !== 0){
                        text += ', ';
                    }
                    text += items[i];
                }
            }
            return text;
        }
    }
})();
