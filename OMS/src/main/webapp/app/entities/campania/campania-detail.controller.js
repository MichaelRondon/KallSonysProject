(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('CampaniaDetailController', CampaniaDetailController);

    CampaniaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Campania', 'Producto'];

    function CampaniaDetailController($scope, $rootScope, $stateParams, previousState, entity, Campania, Producto) {
        var vm = this;

        vm.campania = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omsApp:campaniaUpdate', function(event, result) {
            vm.campania = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();