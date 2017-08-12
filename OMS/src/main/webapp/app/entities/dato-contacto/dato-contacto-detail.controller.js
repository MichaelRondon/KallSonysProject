(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('DatoContactoDetailController', DatoContactoDetailController);

    DatoContactoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DatoContacto', 'Proveedor'];

    function DatoContactoDetailController($scope, $rootScope, $stateParams, previousState, entity, DatoContacto, Proveedor) {
        var vm = this;

        vm.datoContacto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omsApp:datoContactoUpdate', function(event, result) {
            vm.datoContacto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
