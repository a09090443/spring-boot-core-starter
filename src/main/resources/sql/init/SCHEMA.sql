/****** 系統設定檔 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [SYS_PARAMETER](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[ParamId] [int] NOT NULL,
	[ParamKey] [nvarchar](256) NOT NULL,
	[ParamValue] [nvarchar](max) NULL,
	[IsPassword] [char] NULL,
	[Description] [nvarchar](max) NULL,
	[UpdatedBy] [nvarchar](50) NULL,
	[UpdatedAt] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[CreatedAt] [datetime] NULL,
 CONSTRAINT [PK_sys_parameter] PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_sys_parameter] UNIQUE NONCLUSTERED
(
	[ParamKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'流水號' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'Id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'參數ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'ParamId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'參數key' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'ParamKey'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'參數值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'ParamValue'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否為密碼' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'IsPassword'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'參數描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'Description'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'變更者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'UpdatedBy'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'變更時間' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'UpdatedAt'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建立者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'CreatedBy'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'變更時間' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER', @level2type=N'COLUMN',@level2name=N'CreatedAt'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系統參數' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_PARAMETER'
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/****** LDAP 使用者 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [LDAP_USER](
	[UserId] [nvarchar](50) NOT NULL,
	[Name] [nvarchar](50) NULL,
	[Email] [nvarchar](50) NULL,
	[LdapDn] [nvarchar](300) NULL,
	[IsEnabled] [char] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'使用者編號' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER', @level2type=N'COLUMN',@level2name=N'UserId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'使用者名稱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER', @level2type=N'COLUMN',@level2name=N'Name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'使用者信箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER', @level2type=N'COLUMN',@level2name=N'Email'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'使用者DN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER', @level2type=N'COLUMN',@level2name=N'LdapDn'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否開放使用者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER', @level2type=N'COLUMN',@level2name=N'IsEnabled'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'LDAP使用者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER'
GO

/****** LDAP user 登出入紀錄 ******/
CREATE TABLE [LDAP_USER_LOG](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[UserId] [nvarchar](30) NOT NULL,
	[Status] [nvarchar](15) NOT NULL,
	[Time] [datetime] NOT NULL
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'流水號' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER_LOG', @level2type=N'COLUMN',@level2name=N'Id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'使用者編號' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER_LOG', @level2type=N'COLUMN',@level2name=N'UserId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活動狀態' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER_LOG', @level2type=N'COLUMN',@level2name=N'Status'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'活動時間紀錄' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER_LOG', @level2type=N'COLUMN',@level2name=N'Time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'使用者活動紀錄' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LDAP_USER_LOG'
GO

/****** 系統選單 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [SYS_MENU](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[MenuId] [nvarchar](20) NOT NULL,
	[MenuName] [varchar](100) NOT NULL,
	[Path] [varchar](100) NULL,
	[Comment] [varchar](200) NULL,
	[OrderId] [int] NULL,
	[Enabled] [bit] NULL,
	[ParentId] [nvarchar](20) NULL
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'流水號' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_MENU', @level2type=N'COLUMN',@level2name=N'Id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'選單編號' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_MENU', @level2type=N'COLUMN',@level2name=N'MenuId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'選單名稱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_MENU', @level2type=N'COLUMN',@level2name=N'MenuName'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'選單連結位置' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_MENU', @level2type=N'COLUMN',@level2name=N'Path'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'選單說明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_MENU', @level2type=N'COLUMN',@level2name=N'Comment'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'選單順序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_MENU', @level2type=N'COLUMN',@level2name=N'OrderId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'選單開關' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_MENU', @level2type=N'COLUMN',@level2name=N'Enabled'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系統選單' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYS_MENU'
GO

/****** 系統錯誤紀錄 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [ERROR_LOG](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[ServerIp] [varchar](30) NULL,
	[Message] [nvarchar](max) NULL,
	[Time] [datetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
	[UpdatedAt] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'流水號' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG', @level2type=N'COLUMN',@level2name=N'Id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系統IP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG', @level2type=N'COLUMN',@level2name=N'ServerIp'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'問題描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG', @level2type=N'COLUMN',@level2name=N'Message'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'發生時間' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG', @level2type=N'COLUMN',@level2name=N'Time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'變更者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG', @level2type=N'COLUMN',@level2name=N'UpdatedBy'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'變更時間' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG', @level2type=N'COLUMN',@level2name=N'UpdatedAt'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建立者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG', @level2type=N'COLUMN',@level2name=N'CreatedBy'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建立時間' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG', @level2type=N'COLUMN',@level2name=N'CreatedAt'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系統錯誤紀錄' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ERROR_LOG'
GO
