package sh.models

object LinkStatus extends Enumeration{
  type LinkStatus= Value
  val PROCESSED /*The link has been processed and extracted*/, 
  	STORED /*The link has been saved into database but not processed yet*/ = Value
}