try:
    import unittest2 as unittest
except ImportError:
    import unittest

from pika import utils


class UtilsTests(unittest.TestCase):

    def test_is_callable_true(self):
        self.assertTrue(utils.is_callable(utils.is_callable))

    def test_is_callable_false(self):
        self.assertFalse(utils.is_callable(1))
