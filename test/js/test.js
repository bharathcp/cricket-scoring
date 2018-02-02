var assert = require('assert');
require('./cricketCalc.js')

describe('Array', function() {
  describe('#indexOf()', function() {
    it('should return -1 when the value is not present', function() {
      assert.equal(-1, [1,2,3].indexOf(4));
    });
  });
});

describe('Cricket', function() {
	describe('enter', function() {
		it('should add the number to the score', function() {
			assert.equal(0, score);
		});
	});
});