(function () {
    'use strict';

    angular
            // .module('omsApp')
            .module('omsApp')
            .controller('ProductoDialogController', ProductoDialogController);

    ProductoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Producto', 'ProveedorProducto', 'Categoria', 'Proveedor'];

    function ProductoDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Producto, ProveedorProducto, Categoria, Proveedor) {
        var vm = this;

        vm.tags;
        vm.producto = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.proveedorproductos = ProveedorProducto.query();
        vm.categorias = Categoria.query();
        vm.proveedores = Proveedor.query();
        vm.proveedoresChecked = [];
        vm.idProductEnProv = [];
        vm.producImageSmallBaseUrl = 'http://laptop-diego:9091/api/ImageSmall/';
        vm.producImageMediumBaseUrl = 'http://laptop-diego:9091/api/ImageMedium/';
        vm.producImageLargeBaseUrl = 'http://laptop-diego:9091/api/ImageLarge/';

        if (vm.producto.keyWords) {
            vm.tags = [];
            vm.producto.keyWords.forEach(loadTagsFromKeyWords);
        }

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.producto.keyWords = [];
            if (vm.tags) {
                vm.tags.forEach(saveTagTextOnKeyWord);
            }
            var indexProv = 0;
            vm.producto.proveedores = [];
            var proveedorIndex;
            for (proveedorIndex in vm.proveedores) {
                var provId_ = vm.proveedores[proveedorIndex].id
                if (vm.proveedoresChecked[provId_]) {
                    vm.producto.proveedores[indexProv] = {"id": -1, "idProducto": "-1"};
                    vm.producto.proveedores[indexProv].id = provId_;
                    vm.producto.proveedores[indexProv].idProducto = vm.idProductEnProv[provId_];
                    indexProv++;
                }
            }
            alert(angular.toJson(vm.producto));
            vm.isSaving = true;
            if (vm.producto.id !== null) {
                Producto.update(vm.producto, onSaveSuccess, onSaveError);
            } else {
                Producto.save(vm.producto, onSaveSuccess, onSaveError);
            }
        }

        function saveTagTextOnKeyWord(item, index) {
            vm.producto.keyWords[index] = item.text;
        }

        function loadTagsFromKeyWords(item, index) {
            vm.tags[index] = item;
        }

        vm.stateChanged = function stateChanged(proveedorId) {
        }

        function onSaveSuccess(result) {
            $scope.$emit('omsApp:productoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function smallImage(id) {
            if (id) {
                return vm.producImageSmallBaseUrl + vm.producto.id;
            }
            return vm.producImageSmallBaseUrl + 1;
        }

        function mediumImage(id) {
            if (id) {
                return vm.producImageMediumBaseUrl + vm.producto.id;
            }
            return vm.producImageMediumBaseUrl + 1;
        }

        function largeImage(id) {
            if (id) {
                return vm.producImageLargeBaseUrl + vm.producto.id;
            }
            return vm.producImageLargeBaseUrl + 1;
        }

        vm.datePickerOpenStatus.fechaRevDisponibilidad = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
