(function () {
    'use strict';

    angular
            .module('omsApp')
            .controller('RankingOrdenesCerrradasController', RankingOrdenesCerrradasController);

    RankingOrdenesCerrradasController.$inject = ['Producto', 'ParseLinks', 'AlertService', 'paginationConstants', 'Ordenes', 'pagingParams'];

    function RankingOrdenesCerrradasController(Producto, ParseLinks, AlertService, paginationConstants, Ordenes, pagingParams) {

        var vm = this;

        vm.fechaInicio;
        vm.fechaFin;
        
//        vm.fechaInicio = {
//            date: new Date('2015-03-01T00:00:00Z')
//        };
//
//        vm.fechaFin = {
//            date: new Date('2015-03-01T12:30:00Z')
//        };
        
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.fechaInicio = false;
        vm.datePickerOpenStatus.fechaFin = false;
        
//        vm.fechaInicio.maxDate = vm.fechaFin.date;
//        vm.fechaFin.minDate = vm.fechaInicio.date;

        vm.consultar = consultar;
        vm.openCalendar = openCalendar;
        vm.page = 0;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.ordenes = [];
        vm.showResults = {
            show: false
        };

        vm.producto = null;
        vm.loadPage = loadPage;
        vm.links = {
            last: 0
        };
        vm.reset = reset;
//        loadAll();

        function consultar() {
            if (!vm.fechaInicio) {
                return;
            }
            if (!vm.fechaFin) {
                return;
            }
            Ordenes.rankingCerradas({
                page: vm.page,
                size: vm.itemsPerPage,
                fechaInicio: vm.fechaInicio,
                fechaFin: vm.fechaFin,
                sort: sort()

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
                vm.ordenes = data;
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
            vm.ordenes = [];
        }

        function loadPage(page) {
            vm.page = page;
        }

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
