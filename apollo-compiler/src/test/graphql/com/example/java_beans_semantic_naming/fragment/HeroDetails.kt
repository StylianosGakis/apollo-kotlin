// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.java_beans_semantic_naming.fragment

import com.apollographql.apollo.api.GraphqlFragment
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.internal.ResponseFieldMapper
import com.apollographql.apollo.api.internal.ResponseFieldMarshaller
import com.apollographql.apollo.api.internal.ResponseReader
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter")
data class HeroDetails(
  val __typename: String = "Character",
  /**
   * The name of the character
   */
  val name: String,
  /**
   * The friends of the character exposed as a connection with edges
   */
  val friendsConnection: FriendsConnection,
  val asDroid: AsDroid?
) : GraphqlFragment {
  override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
    writer.writeString(RESPONSE_FIELDS[0], this@HeroDetails.__typename)
    writer.writeString(RESPONSE_FIELDS[1], this@HeroDetails.name)
    writer.writeObject(RESPONSE_FIELDS[2], this@HeroDetails.friendsConnection.marshaller())
    writer.writeFragment(this@HeroDetails.asDroid?.marshaller())
  }

  companion object {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
        ResponseField.forString("__typename", "__typename", null, false, null),
        ResponseField.forString("name", "name", null, false, null),
        ResponseField.forObject("friendsConnection", "friendsConnection", null, false, null),
        ResponseField.forFragment("__typename", "__typename", listOf(
          ResponseField.Condition.typeCondition(arrayOf("Droid"))
        ))
        )

    val FRAGMENT_DEFINITION: String = """
        |fragment HeroDetails on Character {
        |  __typename
        |  name
        |  friendsConnection {
        |    __typename
        |    totalCount
        |    edges {
        |      __typename
        |      node {
        |        __typename
        |        name
        |      }
        |    }
        |    pageInfo {
        |      __typename
        |      hasNextPage
        |    }
        |    isEmpty
        |  }
        |  ... on Droid {
        |    name
        |    primaryFunction
        |  }
        |}
        """.trimMargin()

    operator fun invoke(reader: ResponseReader): HeroDetails = reader.run {
      val __typename = readString(RESPONSE_FIELDS[0])
      val name = readString(RESPONSE_FIELDS[1])
      val friendsConnection = readObject<FriendsConnection>(RESPONSE_FIELDS[2]) { reader ->
        FriendsConnection(reader)
      }
      val asDroid = readFragment<AsDroid>(RESPONSE_FIELDS[3]) { reader ->
        AsDroid(reader)
      }
      HeroDetails(
        __typename = __typename,
        name = name,
        friendsConnection = friendsConnection,
        asDroid = asDroid
      )
    }

    @Suppress("FunctionName")
    fun Mapper(): ResponseFieldMapper<HeroDetails> = ResponseFieldMapper { invoke(it) }
  }

  data class Node(
    val __typename: String = "Character",
    /**
     * The name of the character
     */
    val name: String
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@Node.__typename)
      writer.writeString(RESPONSE_FIELDS[1], this@Node.name)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("name", "name", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): Node = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val name = readString(RESPONSE_FIELDS[1])
        Node(
          __typename = __typename,
          name = name
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<Node> = ResponseFieldMapper { invoke(it) }
    }
  }

  data class Edge(
    val __typename: String = "FriendsEdge",
    /**
     * The character represented by this friendship edge
     */
    val node: Node?
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@Edge.__typename)
      writer.writeObject(RESPONSE_FIELDS[1], this@Edge.node?.marshaller())
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forObject("node", "node", null, true, null)
          )

      operator fun invoke(reader: ResponseReader): Edge = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val node = readObject<Node>(RESPONSE_FIELDS[1]) { reader ->
          Node(reader)
        }
        Edge(
          __typename = __typename,
          node = node
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<Edge> = ResponseFieldMapper { invoke(it) }
    }
  }

  data class PageInfo(
    val __typename: String = "PageInfo",
    val hasNextPage: Boolean
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@PageInfo.__typename)
      writer.writeBoolean(RESPONSE_FIELDS[1], this@PageInfo.hasNextPage)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forBoolean("hasNextPage", "hasNextPage", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): PageInfo = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val hasNextPage = readBoolean(RESPONSE_FIELDS[1])
        PageInfo(
          __typename = __typename,
          hasNextPage = hasNextPage
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<PageInfo> = ResponseFieldMapper { invoke(it) }
    }
  }

  data class FriendsConnection(
    val __typename: String = "FriendsConnection",
    /**
     * The total number of friends
     */
    val totalCount: Int?,
    /**
     * The edges for each of the character's friends.
     */
    val edges: List<Edge?>?,
    /**
     * Information for paginating this connection
     */
    val pageInfo: PageInfo,
    /**
     * For test java beans semantic naming only
     */
    val isEmpty: Boolean
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@FriendsConnection.__typename)
      writer.writeInt(RESPONSE_FIELDS[1], this@FriendsConnection.totalCount)
      writer.writeList(RESPONSE_FIELDS[2], this@FriendsConnection.edges) { value, listItemWriter ->
        value?.forEach { value ->
          listItemWriter.writeObject(value?.marshaller())}
      }
      writer.writeObject(RESPONSE_FIELDS[3], this@FriendsConnection.pageInfo.marshaller())
      writer.writeBoolean(RESPONSE_FIELDS[4], this@FriendsConnection.isEmpty)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forInt("totalCount", "totalCount", null, true, null),
          ResponseField.forList("edges", "edges", null, true, null),
          ResponseField.forObject("pageInfo", "pageInfo", null, false, null),
          ResponseField.forBoolean("isEmpty", "isEmpty", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): FriendsConnection = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val totalCount = readInt(RESPONSE_FIELDS[1])
        val edges = readList<Edge>(RESPONSE_FIELDS[2]) { reader ->
          reader.readObject<Edge> { reader ->
            Edge(reader)
          }
        }
        val pageInfo = readObject<PageInfo>(RESPONSE_FIELDS[3]) { reader ->
          PageInfo(reader)
        }
        val isEmpty = readBoolean(RESPONSE_FIELDS[4])
        FriendsConnection(
          __typename = __typename,
          totalCount = totalCount,
          edges = edges,
          pageInfo = pageInfo,
          isEmpty = isEmpty
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<FriendsConnection> = ResponseFieldMapper { invoke(it) }
    }
  }

  interface HeroDetailCharacter {
    fun marshaller(): ResponseFieldMarshaller
  }

  data class Node1(
    val __typename: String = "Character",
    /**
     * The name of the character
     */
    val name: String
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@Node1.__typename)
      writer.writeString(RESPONSE_FIELDS[1], this@Node1.name)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("name", "name", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): Node1 = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val name = readString(RESPONSE_FIELDS[1])
        Node1(
          __typename = __typename,
          name = name
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<Node1> = ResponseFieldMapper { invoke(it) }
    }
  }

  data class Edge1(
    val __typename: String = "FriendsEdge",
    /**
     * The character represented by this friendship edge
     */
    val node: Node1?
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@Edge1.__typename)
      writer.writeObject(RESPONSE_FIELDS[1], this@Edge1.node?.marshaller())
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forObject("node", "node", null, true, null)
          )

      operator fun invoke(reader: ResponseReader): Edge1 = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val node = readObject<Node1>(RESPONSE_FIELDS[1]) { reader ->
          Node1(reader)
        }
        Edge1(
          __typename = __typename,
          node = node
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<Edge1> = ResponseFieldMapper { invoke(it) }
    }
  }

  data class PageInfo1(
    val __typename: String = "PageInfo",
    val hasNextPage: Boolean
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@PageInfo1.__typename)
      writer.writeBoolean(RESPONSE_FIELDS[1], this@PageInfo1.hasNextPage)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forBoolean("hasNextPage", "hasNextPage", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): PageInfo1 = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val hasNextPage = readBoolean(RESPONSE_FIELDS[1])
        PageInfo1(
          __typename = __typename,
          hasNextPage = hasNextPage
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<PageInfo1> = ResponseFieldMapper { invoke(it) }
    }
  }

  data class FriendsConnection1(
    val __typename: String = "FriendsConnection",
    /**
     * The total number of friends
     */
    val totalCount: Int?,
    /**
     * The edges for each of the character's friends.
     */
    val edges: List<Edge1?>?,
    /**
     * Information for paginating this connection
     */
    val pageInfo: PageInfo1,
    /**
     * For test java beans semantic naming only
     */
    val isEmpty: Boolean
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@FriendsConnection1.__typename)
      writer.writeInt(RESPONSE_FIELDS[1], this@FriendsConnection1.totalCount)
      writer.writeList(RESPONSE_FIELDS[2], this@FriendsConnection1.edges) { value, listItemWriter ->
        value?.forEach { value ->
          listItemWriter.writeObject(value?.marshaller())}
      }
      writer.writeObject(RESPONSE_FIELDS[3], this@FriendsConnection1.pageInfo.marshaller())
      writer.writeBoolean(RESPONSE_FIELDS[4], this@FriendsConnection1.isEmpty)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forInt("totalCount", "totalCount", null, true, null),
          ResponseField.forList("edges", "edges", null, true, null),
          ResponseField.forObject("pageInfo", "pageInfo", null, false, null),
          ResponseField.forBoolean("isEmpty", "isEmpty", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): FriendsConnection1 = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val totalCount = readInt(RESPONSE_FIELDS[1])
        val edges = readList<Edge1>(RESPONSE_FIELDS[2]) { reader ->
          reader.readObject<Edge1> { reader ->
            Edge1(reader)
          }
        }
        val pageInfo = readObject<PageInfo1>(RESPONSE_FIELDS[3]) { reader ->
          PageInfo1(reader)
        }
        val isEmpty = readBoolean(RESPONSE_FIELDS[4])
        FriendsConnection1(
          __typename = __typename,
          totalCount = totalCount,
          edges = edges,
          pageInfo = pageInfo,
          isEmpty = isEmpty
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<FriendsConnection1> = ResponseFieldMapper { invoke(it) }
    }
  }

  data class AsDroid(
    val __typename: String = "Droid",
    /**
     * What others call this droid
     */
    val name: String,
    /**
     * The friends of the droid exposed as a connection with edges
     */
    val friendsConnection: FriendsConnection1,
    /**
     * This droid's primary function
     */
    val primaryFunction: String?
  ) : HeroDetailCharacter {
    override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@AsDroid.__typename)
      writer.writeString(RESPONSE_FIELDS[1], this@AsDroid.name)
      writer.writeObject(RESPONSE_FIELDS[2], this@AsDroid.friendsConnection.marshaller())
      writer.writeString(RESPONSE_FIELDS[3], this@AsDroid.primaryFunction)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("name", "name", null, false, null),
          ResponseField.forObject("friendsConnection", "friendsConnection", null, false, null),
          ResponseField.forString("primaryFunction", "primaryFunction", null, true, null)
          )

      operator fun invoke(reader: ResponseReader): AsDroid = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])
        val name = readString(RESPONSE_FIELDS[1])
        val friendsConnection = readObject<FriendsConnection1>(RESPONSE_FIELDS[2]) { reader ->
          FriendsConnection1(reader)
        }
        val primaryFunction = readString(RESPONSE_FIELDS[3])
        AsDroid(
          __typename = __typename,
          name = name,
          friendsConnection = friendsConnection,
          primaryFunction = primaryFunction
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<AsDroid> = ResponseFieldMapper { invoke(it) }
    }
  }
}
