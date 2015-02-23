package sh.models

import javax.persistence._

@Entity
@Table(name = "link_content")
class LinkContent {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @ManyToOne
  @JoinColumn(name = "link_id")
  var link: Link = _

  var content: String = _
  var title: String = _

  override def toString() = s"LinkContent: $link"
}