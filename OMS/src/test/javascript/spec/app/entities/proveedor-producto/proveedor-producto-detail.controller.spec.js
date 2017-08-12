'use strict';

describe('Controller Tests', function() {

    describe('ProveedorProducto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProveedorProducto, MockProveedor, MockProducto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProveedorProducto = jasmine.createSpy('MockProveedorProducto');
            MockProveedor = jasmine.createSpy('MockProveedor');
            MockProducto = jasmine.createSpy('MockProducto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ProveedorProducto': MockProveedorProducto,
                'Proveedor': MockProveedor,
                'Producto': MockProducto
            };
            createController = function() {
                $injector.get('$controller')("ProveedorProductoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:proveedorProductoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
