(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('TarjetaDetailController', TarjetaDetailController);

    TarjetaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tarjeta'];

    function TarjetaDetailController($scope, $rootScope, $stateParams, previousState, entity, Tarjeta) {
        var vm = this;

        vm.tarjeta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omsApp:tarjetaUpdate', function(event, result) {
            vm.tarjeta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
