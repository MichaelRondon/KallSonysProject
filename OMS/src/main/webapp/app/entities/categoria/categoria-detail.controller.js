(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('CategoriaDetailController', CategoriaDetailController);

    CategoriaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Categoria'];

    function CategoriaDetailController($scope, $rootScope, $stateParams, previousState, entity, Categoria) {
        var vm = this;

        vm.categoria = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omsApp:categoriaUpdate', function(event, result) {
            vm.categoria = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
