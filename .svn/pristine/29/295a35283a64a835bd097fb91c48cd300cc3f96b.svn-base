package jp.co.MarutouCompack.baseClass;


public enum EnumStatus {
    STATUS_OK(0, "OK"),
    STATUS_ERROR_TIMEOUT(-1, "処理が時間内に完了しませんでした。"),
    STATUS_ERROR_BACKUP(-2, "バックアップに失敗しました。"),
    STATUS_UNKNOWN(-3, "何らかのエラーが発生しました。"),
    STATUS_ERROR_GET_INPUTSTREAM(-4, "入力ストリームの取得に失敗しました。"),
    STATUS_ERROR_GET_OUTPUTSTREAM(-5, "出力ストリームの取得に失敗しました。"),
    STATUS_ERROR_CONNECT(-6, "接続に失敗しました。"),
    STATUS_ERROR_RECEIVE(-7, "データ受信に失敗しました。"),
    STATUS_ERROR_SEND(-8, "データ送信に失敗しました。"),
    STATUS_ACCESS_DENIED(0xC0000022, "アクセスが拒否されました。\nアクセス権を確認してください。"),
    STATUS_ACCESS_VIOLATION(0xC0000005, "アクセスできないメモリ位置に対する読み取りまたは書き込みです。"),
    STATUS_ACCOUNT_DISABLED(0xC0000072, "参照したアカウントは現在無効であり、ログオンできません。"),
    STATUS_ACCOUNT_LOCKED_OUT(0xC0000234, "無効なログオンまたはパスワードの変更の要求が多すぎたため、このユーザーアカウントは自動的にロックされました。"),
    STATUS_ACCOUNT_RESTRICTION(0xC000006E, "参照したユーザー名と認証情報は正しいが、一部のユーザーアカウント制限 (時間帯の制限など) によって認証が失敗したことを示します。"),
    STATUS_BAD_NETWORK_NAME(0xC00000CC, "指定した共有名がリモートサーバーで見つかりません。"),
    STATUS_BUFFER_TOO_SMALL(0xC0000023, "バッファの容量不足のため、エントリを格納できません。\n情報はバッファに書き込まれませんでした。"),
    STATUS_CANNOT_DELETE(0xC0000121, "削除できないファイルまたはディレクトリを削除しようとしました。"),
    STATUS_CANT_ACCESS_DOMAIN_INFO(0xC00000DA, "ドメイン コントローラから構成情報を読み取れませんでした。\nコンピュータが有効ではないか、またはアクセスが拒否されました。"),
    STATUS_DELETE_PENDING(0xC0000056, "削除保留のファイルに対してクローズ以外の操作が要求されました。"),
    STATUS_DUPLICATE_NAME(0xC00000BD, "ネットワーク上に同じ名前があります。"),
    STATUS_FILE_IS_A_DIRECTORY(0xC00000BA, "操作対象として指定したファイルはディレクトリですが、呼び出し側はディレクトリ以外のファイルであると指定しました。"),
    STATUS_INSTANCE_NOT_AVAILABLE(0xC00000AB, "名前付きパイプ インスタンスの最大数に到達しました。"),
    STATUS_INVALID_COMPUTER_NAME(0xC0000122, "リモート コンピュータ名として指定した名前の構文が無効であることを示します。"),
    STATUS_INVALID_HANDLE(0xC0000008, "無効なハンドルを指定しました。"),
    STATUS_INVALID_INFO_CLASS(0xC0000003, "指定した情報クラスは指定したオブジェクトに対して有効な情報クラスではありません。"),
    STATUS_INVALID_LOGON_HOURS(0xC000006F, "ユーザー アカウントでログオン時間が制限されているため、現在はログオンできません。"),
    STATUS_INVALID_PARAMETER(0xC00000EF, "無効なパラメータを引数としてサービスまたは関数に渡しました。"),
    STATUS_INVALID_PIPE_STATE(0xC00000AD, "名前付きパイプは接続状態または終了状態ではありません。"),
    STATUS_INVALID_SID(0xC0000078, "SID 構造体が正しくないことを示します。"),
    STATUS_INVALID_WORKSTATION(0xC0000070, "ユーザー アカウントは、要求元ワークステーションからのログオンには使用できないように制限されています。"),
    STATUS_IO_REPARSE_TAG_NOT_HANDLED(0xC0000279, "この IO タグのための複数層のファイル システム ドライバは必要なときにタグを処理しませんでした。"),
    STATUS_LOGON_FAILURE(0xC000006D, "実行しようとしたログオンは無効です。\nユーザー名または認証情報に誤りがあります。"),
    STATUS_LOGON_TYPE_NOT_GRANTED(0xC000015B, "ユーザーが許可されていないログオンの種類 (たとえば、対話型やネットワーク) を要求しました。\n管理者は対話方式またはネットワークを介してログオンできるユーザーを制御しています。"),
    STATUS_MORE_PROCESSING_REQUIRED(0xC0000016, "指定した I/O 要求パケット (IRP) は、I/O 操作が完了していないため、後処理できません。"),
    STATUS_NETWORK_ACCESS_DENIED(0xC00000CA, "ネットワーク アクセスは拒否されました。"),
    STATUS_NETWORK_NAME_DELETED(0xC00000C9, "ネットワーク名は削除されました。"),
    STATUS_NO_LOGON_SERVERS(0xC000005E, "現在、ログオン要求を処理できるログオン サーバーはありません。"),
    STATUS_NO_SUCH_ALIAS(0xC0000151, "指定されたローカル グループは存在しません。"),
    STATUS_NO_SUCH_DEVICE(0xC000000E, "存在しないデバイスを指定しました。"),
    STATUS_NO_SUCH_DOMAIN(0xC00000DF, "指定されたドメインは存在しません。"),
    STATUS_NO_SUCH_FILE(0xC000000F, "ファイルが見つかりません"),
    STATUS_NO_SUCH_USER(0xC0000064, "指定されたユーザーは存在しません。"),
    STATUS_NO_TRUST_SAM_ACCOUNT(0xC000018B, "Windows Server 上の SAM データベースがこのワークステーションの信頼関係に対するコンピュータ アカウントを持っていません。"),
    STATUS_NOLOGON_WORKSTATION_TRUST_ACCOUNT(0xC0000199, "使用されているアカウントはコンピュータ アカウントです。このサーバーにアクセスするには、グローバル ユーザー アカウントまたはローカル ユーザー アカウントを使用してください。"),
    STATUS_NONE_MAPPED(0xC0000073, "情報はまったく変換されませんでした。"),
    STATUS_NOT_A_DIRECTORY(0xC0000103, "要求したファイルはディレクトリではありません。"),
    STATUS_NOT_FOUND(0xC0000225, "オブジェクトが見つかりませんでした。"),
    STATUS_NOT_IMPLEMENTED(0xC0000002, "要求した操作は実装されていません。"),
    STATUS_OBJECT_NAME_COLLISION(0xC0000035, "オブジェクト名は既に存在します。"),
    STATUS_OBJECT_NAME_INVALID(0xC0000033, "オブジェクト名が無効です。"),
    STATUS_OBJECT_NAME_NOT_FOUND(0xC0000034, "オブジェクト名が見つかりません。"),
    STATUS_OBJECT_PATH_INVALID(0xC0000039, "オブジェクト パス コンポーネントがディレクトリ オブジェクトではありません。"),
    STATUS_OBJECT_PATH_NOT_FOUND(0xC000003A, "パスが見つかりません"),
    STATUS_OBJECT_PATH_SYNTAX_BAD(0xC000003B, "オブジェクト パス コンポーネントがディレクトリ オブジェクトではありません。"),
    STATUS_PASSWORD_EXPIRED(0xC0000071, "ユーザー アカウントのパスワードの有効期限が切れています。"),
    STATUS_PASSWORD_MUST_CHANGE(0xC0000224, "ユーザーのパスワードを最初にログオンする前に変更しなければなりません。"),
    STATUS_PATH_NOT_COVERED(0xC0000257, "接続したサーバーは DFS 名前空間の一部をサポートしません。"),
    STATUS_PIPE_BROKEN(0xC000014B, "パイプの他端が閉じているため、パイプ操作が失敗しました。"),
    STATUS_PIPE_BUSY(0xC00000AE, "指定したパイプは操作を完了するように設定されており、現在の I/O 操作はキューに登録されているため、パイプをキュー操作に変更することはできません。"),
    STATUS_PIPE_CLOSING(0xC00000B1, "指定した名前付きパイプは終了状態です。"),
    STATUS_PIPE_DISCONNECTED(0xC00000B0, "指定した名前付きパイプは切断状態です。"),
    STATUS_PIPE_LISTENING(0xC00000B3, "指定した名前付きパイプは受信状態です。"),
    STATUS_PIPE_NOT_AVAILABLE(0xC00000AC, "受信状態の名前付きパイプのインスタンスが見つかりません。"),
    STATUS_PORT_DISCONNECTED(0xC0000037, "切断された通信ポートにメッセージを送信しようとしました。"),
    STATUS_REQUEST_NOT_ACCEPTED(0xC00000D0, "既に接続数が最大に達しているため、これ以上このリモート コンピュータに接続できません。"),
    STATUS_SHARING_VIOLATION(0xC0000043, "共有アクセス フラグに互換性がないため、ファイルを開けません。"),
    STATUS_TRUSTED_DOMAIN_FAILURE(0xC000018C, "プライマリ ドメインと信頼される側のドメインの間の信頼関係の確立に失敗したため、ログオン要求は失敗しました。"),
    STATUS_UNSUCCESSFUL(0xC0000001, "要求した操作が失敗しました。"),
    STATUS_USER_EXISTS(0xC0000063, "指定されたユーザーは既に存在します。"),
    STATUS_WRONG_PASSWORD(0xC000006A, "パスワードを更新しようとしたときに、このリターン状態は、現在のパスワードとして指定した値が正しくないことを示します。"),
    STATUS_UPDATE(-9, "アプリ更新有り");

    private final int mCode;
    private final String mStatus;
    EnumStatus(int code, String status){
        mCode = code;
        mStatus = status;
    }

    public String getStatus(){
        return mStatus;
    }

    public int getCode(){
        return mCode;
    }
}
