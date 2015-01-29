package org.levin.hadoop.hdfs.dirs;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.permission.AclEntry;
import org.apache.hadoop.fs.permission.PermissionStatus;
import org.apache.hadoop.hdfs.server.namenode.AclStorage;
import org.apache.hadoop.hdfs.server.namenode.INode;
import org.apache.hadoop.hdfs.server.namenode.INodeDirectory;
import org.apache.hadoop.hdfs.server.namenode.INodeMap;
import org.apache.hadoop.hdfs.server.namenode.INodesInPath;
import org.apache.hadoop.hdfs.server.namenode.snapshot.Snapshot;

public class LevinFSDirectory {
    private final LevinFSNamesystem namesystem;
    private final INodeDirectory rootDir;
    private final INodeMap inodeMap;
    
    public LevinFSDirectory(LevinFSNamesystem namesystem, Configuration conf) {
        this.namesystem = namesystem;
        this.rootDir = LevinFSDirectoryUtil.createRoot(namesystem);
        this.inodeMap = INodeMap.newInstance(rootDir);
    }
    
    public INode unprotectedMkdir(long inodeId, String src, PermissionStatus perms,
            List<AclEntry> aclEntries, long timestamp) {
        byte[][] components = INode.getPathComponents(src);
        INodesInPath iip = getExistingPathINodes(components);
        INode[] inodes = iip.getINodes();
        final int pos = inodes.length - 1;
        unprotectedMkdir(inodeId, iip, pos, components[pos], 
                perms, aclEntries, timestamp);
        return inodes[pos];
    }
    
    private void unprotectedMkdir(long inodeId, INodesInPath inodesInPath, int pos,
            byte[] name, PermissionStatus perms, List<AclEntry> aclEntries, long timestamp) {
        INodeDirectory dir = new INodeDirectory(inodeId, name, perms, timestamp);
        if (addChild(inodesInPath, pos, dir, true)) {
            if (aclEntries != null) {
                AclStorage.updateINodeAcl(dir, aclEntries, Snapshot.CURRENT_STATE_ID);
            }
            inodesInPath.setINode(pos, dir);
        }
    }
    
    public INodeDirectory getRootDir() {
        return rootDir;
    }
    
    public INodeMap getINodeMap() {
        return inodeMap;
    }
}
