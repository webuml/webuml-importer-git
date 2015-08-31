package com.webuml.importer.git.jgit;

import com.webuml.importer.parser.VirtualFile;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GitRepositoryInstance {

  private static final String NULL_OBJECT = "AbbreviatedObjectId[0000000000000000000000000000000000000000]";
  private JGitConfiguration configuration;
  private Git git;

  public GitRepositoryInstance(JGitConfiguration configuration) {
    this.configuration = configuration;
  }

  public void createRepoAtDirectory(String repo) throws IOException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    File gitDir = new File(configuration.getGitBaseDirectory(repo));
    Repository repository = builder.findGitDir(gitDir)
        .readEnvironment()
        .findGitDir()
        .build();
    git = new Git(repository);
  }

  public void checkoutRepo(String repo, String targetLocation) throws GitAPIException {
    File directory = new File(configuration.getGitBaseDirectory(targetLocation));
    directory.mkdirs();

    git = Git.cloneRepository()
        .setBare(true)
        .setURI(repo)
        .setDirectory(directory)
        .call();
  }

  public List<VirtualFile> getCurrentFilesFor(String branchName) throws Exception {
    List<VirtualFile> virtualFiles = new ArrayList<>();
    RevCommit commit = getCommit(branchName);
    RevTree tree = commit.getTree();
    TreeWalk treeWalk = new TreeWalk(git.getRepository());
    treeWalk.addTree(tree);
    treeWalk.setRecursive(true);
    while (treeWalk.next()) {
      if (treeWalk.isSubtree()) {
        treeWalk.enterSubtree();
      }
      else {
        ObjectLoader loader = git.getRepository().open(treeWalk.getObjectId(0));
        ObjectStream objectStream = loader.openStream();
        VirtualFile virtualFile = creteVirualFile(objectStream, treeWalk.getNameString());
        virtualFiles.add(virtualFile);
      }
    }
    return virtualFiles;
  }

  private RevCommit getCommit(String name)
      throws Exception {
    final RevWalk walk = new RevWalk(git.getRepository());
    final ObjectId id = git.getRepository().resolve(name);
    return walk.parseCommit(id);
  }

  private List<VirtualFile> walkDirectory(File parentFile) {
    List<VirtualFile> virtualFiles = new ArrayList<>();
    for (File file : parentFile.listFiles()) {
      if (file.isFile()) {
        try {
          VirtualFile virtualFile = creteVirualFile(file);
          virtualFiles.add(virtualFile);
        }
        catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
      if (file.isDirectory() && isNotGitDirectory(file)) {
        virtualFiles.addAll(walkDirectory(file));
      }
    }
    return virtualFiles;
  }

  private VirtualFile creteVirualFile(File file) throws FileNotFoundException {
    VirtualFile virtualFile = new VirtualFile();
    virtualFile.setFileStream(new FileInputStream(file));
    virtualFile.setFileName(file.getName());
    return virtualFile;
  }

  private VirtualFile creteVirualFile(ObjectStream objectStream, String name) throws FileNotFoundException {
    VirtualFile virtualFile = new VirtualFile();
    virtualFile.setFileStream(objectStream);
    virtualFile.setFileName(name);
    return virtualFile;
  }

  private boolean isNotGitDirectory(File file) {
    return !file.getName().endsWith(".git");
  }

  public List<DiffEntry> getDiffEntries(ObjectId old, ObjectId head) throws GitAPIException, IOException {
    if (old == null || head == null) {
      throw new RuntimeException("old: " + old + " and new: " + head + " must not be null!");
    }
    ObjectReader reader = git.getRepository().newObjectReader();
    CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
    oldTreeIter.reset(reader, old);
    CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
    newTreeIter.reset(reader, head);
    return git.diff()
        .setNewTree(newTreeIter)
        .setOldTree(oldTreeIter)
        .call();
  }

  public List<VirtualFile> getLastChanges() throws GitAPIException, IOException {
    Iterator<RevCommit> iterator = git.log().call().iterator();
    List<VirtualFile> changes = new ArrayList<>();
    int counter = 0;
    while (iterator.hasNext() && counter < 1) {
      RevCommit next = iterator.next();
      RevCommit[] parents = next.getParents();

      if (parents.length > 0) {
        RevCommit parent = parents[0];
        List<DiffEntry> diffEntries = getDiffEntries(parent.getTree(), next.getTree());
        diffEntries.stream().forEach(entry -> {
          changes.addAll(getChanges(entry));
        });
      }
      counter++;
    }
    return changes;
  }

  private List<VirtualFile> getChanges(DiffEntry entry) {
    ArrayList<VirtualFile> virtualFiles = new ArrayList<>();
    AbbreviatedObjectId oldId = entry.getOldId();
    try {
      if (!NULL_OBJECT.equals(oldId.toString())) {
        ObjectLoader open = git.getRepository().getObjectDatabase().open(oldId.toObjectId());
        VirtualFile virtualFile = new VirtualFile();
        virtualFile.setFileStream(open.openStream());
        virtualFile.setFileName(entry.getOldPath());
        virtualFiles.add(virtualFile);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return virtualFiles;
  }
}
