(function() {
    'use strict';

    angular
        .module('omsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dato-contacto', {
            parent: 'entity',
            url: '/dato-contacto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omsApp.datoContacto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dato-contacto/dato-contactos.html',
                    controller: 'DatoContactoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('datoContacto');
                    $translatePartialLoader.addPart('tipoDatoContacto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dato-contacto-detail', {
            parent: 'dato-contacto',
            url: '/dato-contacto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omsApp.datoContacto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dato-contacto/dato-contacto-detail.html',
                    controller: 'DatoContactoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('datoContacto');
                    $translatePartialLoader.addPart('tipoDatoContacto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DatoContacto', function($stateParams, DatoContacto) {
                    return DatoContacto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dato-contacto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dato-contacto-detail.edit', {
            parent: 'dato-contacto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dato-contacto/dato-contacto-dialog.html',
                    controller: 'DatoContactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DatoContacto', function(DatoContacto) {
                            return DatoContacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dato-contacto.new', {
            parent: 'dato-contacto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dato-contacto/dato-contacto-dialog.html',
                    controller: 'DatoContactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tipoDatoContacto: null,
                                valor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dato-contacto', null, { reload: 'dato-contacto' });
                }, function() {
                    $state.go('dato-contacto');
                });
            }]
        })
        .state('dato-contacto.edit', {
            parent: 'dato-contacto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dato-contacto/dato-contacto-dialog.html',
                    controller: 'DatoContactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DatoContacto', function(DatoContacto) {
                            return DatoContacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dato-contacto', null, { reload: 'dato-contacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dato-contacto.delete', {
            parent: 'dato-contacto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dato-contacto/dato-contacto-delete-dialog.html',
                    controller: 'DatoContactoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DatoContacto', function(DatoContacto) {
                            return DatoContacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dato-contacto', null, { reload: 'dato-contacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
