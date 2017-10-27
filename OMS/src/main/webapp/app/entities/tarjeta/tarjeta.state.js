(function() {
    'use strict';

    angular
        .module('omsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tarjeta', {
            parent: 'entity',
            url: '/tarjeta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omsApp.tarjeta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tarjeta/tarjetas.html',
                    controller: 'TarjetaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tarjeta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tarjeta-detail', {
            parent: 'tarjeta',
            url: '/tarjeta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omsApp.tarjeta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tarjeta/tarjeta-detail.html',
                    controller: 'TarjetaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tarjeta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tarjeta', function($stateParams, Tarjeta) {
                    return Tarjeta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tarjeta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tarjeta-detail.edit', {
            parent: 'tarjeta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarjeta/tarjeta-dialog.html',
                    controller: 'TarjetaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tarjeta', function(Tarjeta) {
                            return Tarjeta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tarjeta.new', {
            parent: 'tarjeta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarjeta/tarjeta-dialog.html',
                    controller: 'TarjetaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tipo: null,
                                numero: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tarjeta', null, { reload: 'tarjeta' });
                }, function() {
                    $state.go('tarjeta');
                });
            }]
        })
        .state('tarjeta.edit', {
            parent: 'tarjeta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarjeta/tarjeta-dialog.html',
                    controller: 'TarjetaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tarjeta', function(Tarjeta) {
                            return Tarjeta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tarjeta', null, { reload: 'tarjeta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tarjeta.delete', {
            parent: 'tarjeta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarjeta/tarjeta-delete-dialog.html',
                    controller: 'TarjetaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tarjeta', function(Tarjeta) {
                            return Tarjeta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tarjeta', null, { reload: 'tarjeta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
