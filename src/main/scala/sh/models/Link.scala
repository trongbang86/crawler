package sh.models

import javax.persistence._

@Entity
@Table(name = "links")
class Link(url: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _
  
  def this()= this(null)
  
  var status: String = _
  
  //when a link is created first time, the default value for touched is one
  var touched: Int= 1
  
  //default value of processed_time is 0, when the page is processed, it is increased
  var processed_time: Int= _
  
    
  @OneToOne(mappedBy="link")
  var linkContent: LinkContent = _
  
  override def toString = id + ")" + url
}