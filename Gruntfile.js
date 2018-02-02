module.exports = function( grunt ) {
    grunt.loadNpmTasks( "grunt-contrib-qunit" );
    grunt.loadNpmTasks( "grunt-qunit-junit" );
    grunt.initConfig( {
    	qunit_junit: {
    		options: {
    			dest: 'build/js'
    		}
        },
        qunit: {
            all: "test/js/test.html"
        }
    } );

        // By default, lint an	d run all tests.
    grunt.registerTask('default', ['qunit_junit', 'qunit']);
};