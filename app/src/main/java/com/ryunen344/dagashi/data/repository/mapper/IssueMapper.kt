package com.ryunen344.dagashi.data.repository.mapper

object IssueMapper {
//fun toEntity(response: IssueRootResponse): List<IssueWithLabelAndComment> {
//    return response.issues.nodes.mapIndexed { index, issueNodeResponse ->
//        IssueWithLabelAndComment(
//            issueEntity = IssueEntity(
//                singleUniqueId = "${response.number}_$index",
//                id = index,
//                number = response.number,
//                url = issueNodeResponse.url,
//                title = issueNodeResponse.title,
//                body = issueNodeResponse.body
//            ),
//            labels = toEntity(issueNodeResponse.labels),
//            comments = toEntity("${response.number}_$index", issueNodeResponse.comments)
//        )
//    }
//}
//
//    @JvmStatic
//    fun toEntity(singleUniqueId: String): StashedIssueEntity {
//        return StashedIssueEntity(singleUniqueId = singleUniqueId)
//    }
//
//    @JvmStatic
//    fun toModel(entity: IssueWithLabelAndCommentOnStash): Issue {
//        return Issue(
//            singleUniqueId = entity.issueWithLabelAndComment.issueEntity.singleUniqueId,
//            url = entity.issueWithLabelAndComment.issueEntity.url,
//            title = entity.issueWithLabelAndComment.issueEntity.title,
//            body = entity.issueWithLabelAndComment.issueEntity.body,
//            labels = entity.issueWithLabelAndComment.labels.map(::toModel),
//            comments = entity.issueWithLabelAndComment.comments.map(::toModel),
//            isStashed = entity.isStashed
//        )
//    }
//
//    @JvmStatic
//    fun toModel(entity: LabelEntity): Label {
//        return Label(
//            name = entity.name,
//            description = entity.description,
//            color = entity.color
//        )
//    }
//
//    @JvmStatic
//    fun toModel(entity: CommentEntity): Comment {
//        return Comment(
//            body = entity.body,
//            publishedAt = entity.publishedAt,
//            author = Author(
//                login = entity.author.login,
//                url = entity.author.url,
//                avatarUrl = entity.author.avatarUrl
//            )
//        )
//    }
//
//    @JvmStatic
//    fun toModel(response: IssueRootResponse): List<Issue> {
//        return response.issues.nodes.mapIndexed { index, node ->
//            Issue(
//                singleUniqueId = "${response.number}_$index",
//                url = node.url,
//                title = node.title,
//                body = node.body,
//                labels = node.labels.nodes.map(::toModel),
//                comments = node.comments.nodes.map(::toModel),
//                isStashed = false
//            )
//        }
//    }
//
//    @JvmStatic
//    fun toModel(node: LabelNodeResponse): Label {
//        return Label(
//            name = node.name,
//            description = node.description,
//            color = node.color
//        )
//    }
//
//    @JvmStatic
//    fun toModel(node: CommentNodeResponse): Comment {
//        return Comment(
//            body = node.body,
//            publishedAt = node.publishedAt,
//            author = Author(
//                login = node.author.login,
//                url = node.author.url,
//                avatarUrl = node.author.avatarUrl
//            )
//        )
//    }
}
