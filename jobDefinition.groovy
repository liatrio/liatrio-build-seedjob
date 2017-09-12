def jenkinsEnvironment = 'default'

try {
    if ( environment )
        jenkinsEnvironment = environment
    println "Using production jenkinsfiles"
}
catch (e) {
    println "Using default jenkinsfiles"
}

def repos = [
    [ url: "https://github.com/liatrio/libotrio.git", prod: "jenkinsfile" , default: "jenkinsfile" ],
    [ url: "https://github.com/liatrio/spring-petclinic.git", prod: "jenkinsfile/full-demo", default: "jenkinsfile"],
    [ url: "https://github.com/liatrio/game-of-life.git", prod: "jenkinsfile", default: "jenkinsfile"],
    [ url: "https://github.com/liatrio/joda-time.git", prod: "jenkinsfile", default: "jenkinsfile"],
    [ url: "https://github.com/liatrio/dromedary", prod: "jenkinsfile", default: "jenkinsfile"],
]

def pipelineJobFolder = folder("pipelineJobs")
repos.each {
    repo ->
        def jobName = repo.url.split("#")[0].split("/")[4].replaceAll(".git", "")
        def jenkinsfileLocation = jenkinsEnvironment == 'prod' ? repo.prod : repo.default
        multibranchPipelineJob("pipelineJobs/${jobName}") {
            branchSources {
                git{
                    remote(repo.url)
                }
            }
            configure {
                    it / factory {
                        scriptPath(jenkinsfileLocation)
                    }
            }
            orphanedItemStrategy {
                discardOldItems {
                    daysToKeep(1)
                }
            }
            triggers {
                periodic(2)
            }
        }
}
pipelineJobFolder.with {
  authorization {
    permission('hudson.model.View.Delete:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.Update:administrators')
    permission('hudson.model.View.Delete:administrators')
    permission('hudson.model.Item.Create:administrators')
    permission('hudson.model.Run.Delete:administrators')
    permission('hudson.model.Item.Workspace:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.Delete:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains:administrators')
    permission('hudson.model.View.Configure:administrators')
    permission('hudson.model.Run.Replay:administrators')
    permission('hudson.model.Item.Configure:administrators')
    permission('hudson.model.View.Read:administrators')
    permission('hudson.model.View.Create:administrators')
    permission('hudson.model.Item.Cancel:administrators')
    permission('hudson.model.Item.Delete:administrators')
    permission('hudson.model.Item.Read:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.View:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.Create:administrators')
    permission('hudson.model.Item.Build:administrators')
    permission('hudson.scm.SCM.Tag:administrators')
    permission('hudson.model.Item.Move:administrators')
    permission('hudson.model.Item.Discover:administrators')
    permission('hudson.model.Run.Update:administrators')
  }
  properties {
    folderLibraries {
        libraries {
          libraryConfiguration {
                name('ldop-shared-library')
                defaultVersion('master')
                implicit(true)
                retriever {
                    modernSCM {
                        scm {
                            git {
                                remote('https://github.com/liatrio/ldop-shared-library.git')
                            }
                        }
                    }
                }
            }
        }
    }
  }
}
