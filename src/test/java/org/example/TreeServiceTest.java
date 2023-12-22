package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TreeServiceTest {

    @InjectMocks
    private TreeService treeService;

    @Mock
    private TreeRepository treeRepository;

    @Test
    public void testSaveTree(){
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        Tree tree = new Tree();
        tree.setInputNumbers(numbers.toString());
        tree.setTreeStructure("{\"value\":2,\"left\":{\"value\":1},\"right\":{\"value\":3}}");

        when(treeRepository.save(any(Tree.class))).thenReturn(tree);

        Tree result = treeService.saveTree(numbers);

        verify(treeRepository, times(1)).save(any(Tree.class));
        assertEquals(tree.getInputNumbers(), result.getInputNumbers());
        assertEquals(tree.getTreeStructure(), result.getTreeStructure());
    }

    @Test
    public void testGetAllTrees() {
        Tree tree1 = new Tree();
        Tree tree2 = new Tree();
        List<Tree> trees = Arrays.asList(tree1, tree2);

        when(treeRepository.findAll()).thenReturn(trees);

        List<Tree> result = treeService.getAllTreesSortedByNewest();

        verify(treeRepository, times(1)).findAll();
        assertEquals(2, result.size());
    }
}