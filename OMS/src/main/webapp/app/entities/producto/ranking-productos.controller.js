(function () {
    'use strict';

    angular
            .module('omsApp')
            .controller('RankingProductosController', RankingProductosController);

    RankingProductosController.$inject = ['Producto', 'ParseLinks', 'AlertService', 'paginationConstants', 'OrdenesReport', 'pagingParams'];

    function RankingProductosController(Producto, ParseLinks, AlertService, paginationConstants, OrdenesReport, pagingParams) {

        var vm = this;

        vm.fechaInicio;
        vm.fechaFin;

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.fechaInicio = false;
        vm.datePickerOpenStatus.fechaFin = false;

        vm.consultar = consultar;
        vm.openCalendar = openCalendar;
        vm.page = 0;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.productImageSmallBaseUrl = 'http://laptop-diego:9091/api/ImageSmall/';
        vm.productos = [];
        vm.showResults = {
            show: false
        };

        vm.loadPage = loadPage;
        vm.links = {
            last: 0
        };
        vm.reset = reset;

        function consultar() {
            if (!vm.fechaInicio) {
                return;
            }
            if (!vm.fechaFin) {
                return;
            }
            Producto.rankingProductos({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort(),
                fechaInicio: vm.fechaInicio,
                fechaFin: vm.fechaFin

            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.productos = data;
                vm.page = pagingParams.page;
                vm.showResults.show = true;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function reset() {
            vm.page = 0;
            vm.rankingClientes = [];
        }

        function loadPage(page) {
            vm.page = page;
        }

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
