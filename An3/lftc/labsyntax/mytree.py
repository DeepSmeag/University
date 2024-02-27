class Node:
    def __init__(self, key):
        self.left = None
        self.right = None
        self.val = key
        self.id = -1

    def __lt__(self, other):
        return self.val.value < other.val.value

    def __gt__(self, other):
        return self.val.value > other.val.value

    def __eq__(self, other):
        return self.val.value == other.val.value


class BinarySearchTree:
    def __init__(self):
        self.root = None
        self.size = 0

    def insert(self, key):
        self.root = self._insert_recursive(self.root, key)

    def _insert_recursive(self, root, key):
        node = Node(key)
        if root is None:
            return node
        if node < root:
            root.left = self._insert_recursive(root.left, key)
        elif node > root:
            root.right = self._insert_recursive(root.right, key)
        return root

    def search(self, key):
        return self._search_recursive(self.root, key)

    def _search_recursive(self, root, key):
        node = Node(key)
        if root is None or root == node:
            return root
        if node < root:
            return self._search_recursive(root.left, key)
        return self._search_recursive(root.right, key)

    def delete(self, key):
        self.root = self._delete_recursive(self.root, key)

    def _delete_recursive(self, root, key):
        node = Node(key)
        if root is None:
            return root
        if node < root:
            root.left = self._delete_recursive(root.left, key)
        elif node > root:
            root.right = self._delete_recursive(root.right, key)
        else:
            if root.left is None:
                return root.right
            elif root.right is None:
                return root.left
            root.val = self._min_value_node(root.right).val
            root.right = self._delete_recursive(root.right, root.val)
        return root

    def _min_value_node(self, node):
        while node.left is not None:
            node = node.left
        return node

    def inorder_traversal(self, print_result=False, category: str = "DEFAULT"):
        result = []
        self._inorder_traversal_recursive(self.root, result)
        if print_result:
            # for constants, we have NUMBER, CHARACTER, DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING
            print(
                "\n\n\n",
                "--------------------------------------------------------------------------------",
                sep="",
            )
            # print table for constants
            print(f"{category:^20}|{'VALUE':^20}|{'LINE NUMBER':^20}|{'POSITION':^20}")
            print(
                "--------------------------------------------------------------------------------"
            )
            for tok in result:
                print(
                    f"{tok.type:^20}|{tok.value:^20}|{tok.lineno:^20}|{tok.lexpos:^20}"
                )
        return result

    def _inorder_traversal_recursive(self, root, result):
        if root:
            self._inorder_traversal_recursive(root.left, result)
            result.append(root.val)
            self._inorder_traversal_recursive(root.right, result)

    def assign_id(self):
        self._assign_id_recursive(self.root)

    def _assign_id_recursive(self, root):
        if root:
            id = self._assign_id_recursive(root.left)
            root.id = self.size
            self.size = self.size + 1
            id = self._assign_id_recursive(root.right)


# Example usage:
if __name__ == "__main__":

    bst = BinarySearchTree()
    bst.insert("501")
    bst.insert("30")
    bst.insert("201")
    bst.insert("40")
    bst.insert("70")
    bst.insert("60")
    bst.insert("80")

    print("Inorder traversal:")
    print(bst.inorder_traversal())

    print("Search for 20:", bst.search("20") is not None)
    print("Search for 100:", bst.search("100") is not None)

    bst.delete("20")
    print("After deleting 20:")
    print(bst.inorder_traversal())
