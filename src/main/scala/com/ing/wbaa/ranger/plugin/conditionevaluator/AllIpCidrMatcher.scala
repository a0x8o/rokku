package com.ing.wbaa.ranger.plugin.conditionevaluator

class AllIpCidrMatcher extends AbstractIpCidrMatcher {
  protected val zero: Boolean = true
  protected def combine(a: Boolean, b: Boolean): Boolean = a && b
}
