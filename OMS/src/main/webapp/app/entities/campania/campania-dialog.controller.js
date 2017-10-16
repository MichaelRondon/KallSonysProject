(function () {
    'use strict';

    angular
            .module('omsApp')
            .controller('CampaniaDialogController', CampaniaDialogController);

    CampaniaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Campania', 'Producto', 'AlertService'];

    function CampaniaDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Campania, Producto, AlertService) {
        var vm = this;

        vm.campania = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.onChange = onChange;
        vm.agregar = agregar;
        vm.eliminar = eliminar;
        vm.nombreProductoBuscar = null;
        vm.productCampaniaBaseUrl = 'http://laptop-diego:9091/api/ImageCampania/';
        vm.productos = [];
//        vm.productos = Producto.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.campania.id !== null) {
                Campania.update(vm.campania, onSaveSuccess, onSaveError);
            } else {
                Campania.save(vm.campania, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('omsApp:campaniaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaInicio = false;
        vm.datePickerOpenStatus.fechaFin = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function onChange() {
            Producto.query({
                page: 0,
//                page: vm.page,
//                size: vm.itemsPerPage,
                size: 20,
                sort: null,
                codigoProducto: null,
                nombreProducto: vm.nombreProductoBuscar,
                descripcion: null
            }, onSuccess, onError);
            function sort() {
//                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
//                if (vm.predicate !== 'id') {
//                    result.push('id');
//                }
//                return result;
            }
            function onSuccess(data, headers) {
//                vm.links = ParseLinks.parse(headers('link'));
//                vm.totalItems = headers('X-Total-Count');
//                for (var i = 0; i < data.length; i++) {
//                    vm.campania.productos.push(data[i]);
//                }
                vm.productos = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function agregar(product) {
            if (!vm.campania.productos) {
                vm.campania.productos = [];
            }
            vm.campania.productos.push(product);
        }

        function eliminar(product) {
            alert("MR" + product);
            var index = vm.campania.productos.indexOf(product);
            if (index > -1) {
                vm.campania.productos.splice(index, 1);
            }
        }
    }
})();
